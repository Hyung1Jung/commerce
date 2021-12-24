package me.hyungil.auth.application.auth.service

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import me.hyungil.auth.application.auth.port.`in`.AuthUseCase
import me.hyungil.auth.application.auth.port.`in`.GetTokenResponse
import me.hyungil.auth.application.auth.port.`in`.GetUserResponse
import me.hyungil.auth.application.auth.port.`in`.LoginRequest
import me.hyungil.auth.application.auth.port.out.AuthPort
import me.hyungil.auth.config.JwtProvider
import me.hyungil.auth.domain.RefreshToken
import me.hyungil.core.error.exception.NotFoundRequestException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authAdapter: AuthPort,
    private val jwtProvider: JwtProvider,
    private val feignUserRemoteService: FeignUserRemoteService
) : AuthUseCase {

    override fun login(loginRequest: LoginRequest): GetTokenResponse {

        val user = Gson().fromJson(getLoginUser(loginRequest), GetUserResponse::class.java)

        val getTokenResponse = jwtProvider.createTokenDto(user.id, user.roles)

        val refreshToken = RefreshToken(secretKey = user.id, token = getTokenResponse.refreshToken)

        authAdapter.save(refreshToken)

        return getTokenResponse
    }

    private fun getLoginUser(loginRequest: LoginRequest) =
        feignUserRemoteService.login(loginRequest) ?: throw NotFoundRequestException()
}