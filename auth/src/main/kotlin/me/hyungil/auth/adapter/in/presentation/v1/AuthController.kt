package me.hyungil.auth.adapter.`in`.presentation.v1

import me.hyungil.auth.application.auth.port.`in`.AuthUseCase
import me.hyungil.auth.application.auth.port.`in`.LoginRequest
import me.hyungil.auth.application.auth.port.`in`.TokenRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/auth")
class AuthController(
    private val authUseCase: AuthUseCase
) {

    @PostMapping("login")
    fun login(@RequestBody loginRequest: LoginRequest) = authUseCase.login(loginRequest)

    @PostMapping("reissue")
    fun reissue(@RequestBody tokenRequest: TokenRequest) = authUseCase.reissue(tokenRequest)
}