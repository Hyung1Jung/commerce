package me.hyungil.auth.application.auth.service

import com.google.gson.Gson
import me.hyungil.auth.application.auth.port.`in`.*
import me.hyungil.auth.application.auth.port.out.AuthPort
import me.hyungil.auth.config.JwtProvider
import me.hyungil.auth.domain.RefreshToken
import me.hyungil.core.error.exception.NotFoundRequestException
import me.hyungil.core.error.exception.UnauthorizedAccessRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val authAdapter: AuthPort,
    private val jwtProvider: JwtProvider,
    private val feignUserRemoteService: FeignUserRemoteService
) : AuthUseCase {

    @Transactional
    override fun login(loginRequest: LoginRequest): GetTokenResponse {

        val user = Gson().fromJson(getLoginUser(loginRequest), GetUserResponse::class.java)

        val getTokenResponse = jwtProvider.createTokenDto(user.id, user.roles)

        val refreshToken = RefreshToken(secretKey = user.id, token = getTokenResponse.refreshToken)

        authAdapter.save(refreshToken)

        return getTokenResponse
    }

    @Transactional
    override fun reissue(tokenRequest: TokenRequest): GetTokenResponse {

        if (!jwtProvider.validationToken(tokenRequest.refreshToken)) throw UnauthorizedAccessRequestException("만료된 refreshToken error 입니다.")

        val accessToken: String = tokenRequest.accessToken
        val authentication = jwtProvider.getAuthentication(accessToken)

        val user = Gson().fromJson(getUserInfo(authentication.name.toLong()), GetUserResponse::class.java)

        val refreshToken = authAdapter.findBySecretKey(user.id) ?: throw NotFoundRequestException()

        if (refreshToken.token != tokenRequest.refreshToken) throw UnauthorizedAccessRequestException("리프레시 토큰이 일치하지 않습니다.")

        val newCreatedToken = jwtProvider.createTokenDto(user.id, user.roles)
        val updateRefreshToken = refreshToken.updateToken(newCreatedToken.refreshToken)

        authAdapter.save(updateRefreshToken)

        return newCreatedToken
    }

    private fun getLoginUser(loginRequest: LoginRequest) =
        feignUserRemoteService.login(loginRequest) ?: throw NotFoundRequestException()

    private fun getUserInfo(id: Long) = feignUserRemoteService.getUserInfo(id) ?: throw NotFoundRequestException()
}