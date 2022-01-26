package me.hyungil.auth.application.auth.service

import com.google.gson.Gson
import me.hyungil.auth.application.auth.port.`in`.*
import me.hyungil.auth.commom.config.JwtProvider
import me.hyungil.core.error.exception.NotFoundRequestException
import me.hyungil.core.error.exception.UnauthorizedAccessRequestException
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit


@Service
class AuthService(
    private val jwtProvider: JwtProvider,
    private val feignUserRemoteService: FeignUserRemoteService,
    private val redisTemplate: StringRedisTemplate
) : AuthUseCase {

    private val refreshTokenValidMillisecond: Long = 14 * 24 * 60 * 60 * 1000L

    @Transactional
    override fun login(loginRequest: LoginRequest): GetTokenResponse {

        val user = Gson().fromJson(getLoginUser(loginRequest), GetUserResponse::class.java)

        val getTokenResponse = jwtProvider.createTokenDto(user.id, user.roles, user.email)

        refreshTokenSave(user.id.toString(), getTokenResponse.refreshToken)

        return getTokenResponse
    }

    private fun refreshTokenSave(userId: String, refreshToken: String) {
        redisTemplate.opsForValue()
            .set("refreshToken : $userId", refreshToken, refreshTokenValidMillisecond, TimeUnit.MILLISECONDS)
    }

    @Transactional
    override fun reissue(tokenRequest: TokenRequest): GetTokenResponse {

        if (!jwtProvider.validationToken(tokenRequest.refreshToken)) throw UnauthorizedAccessRequestException("만료된 refreshToken error 입니다.")

        val accessToken: String = tokenRequest.accessToken
        val authentication = jwtProvider.getAuthentication(accessToken)

        val user = Gson().fromJson(getUserInfo(authentication.name.toLong()), GetUserResponse::class.java)

        val refreshToken = redisTemplate.opsForValue()["refreshToken : ${user.id}"]
        if (refreshToken != tokenRequest.refreshToken) throw UnauthorizedAccessRequestException("리프레시 토큰이 일치하지 않습니다.")

        val newCreatedToken = jwtProvider.createTokenDto(user.id, user.roles, user.email)

        refreshTokenSave(user.id.toString(), newCreatedToken.refreshToken)

        return newCreatedToken
    }

    private fun getLoginUser(loginRequest: LoginRequest) =
        feignUserRemoteService.login(loginRequest) ?: throw NotFoundRequestException()

    private fun getUserInfo(id: Long) = feignUserRemoteService.getUserInfo(id) ?: throw NotFoundRequestException()
}