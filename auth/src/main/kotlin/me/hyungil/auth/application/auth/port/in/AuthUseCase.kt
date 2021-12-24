package me.hyungil.auth.application.auth.port.`in`

interface AuthUseCase {

    fun login(loginRequest: LoginRequest): GetTokenResponse
}