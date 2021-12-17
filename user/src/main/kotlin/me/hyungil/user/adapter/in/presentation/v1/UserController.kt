package me.hyungil.user.adapter.`in`.presentation.v1

import me.hyungil.user.application.user.port.`in`.GetUserResponse
import me.hyungil.user.application.user.port.`in`.UserSignUpRequest
import me.hyungil.user.application.user.port.`in`.UserUseCase
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/users")
@EnableDiscoveryClient
class UserController(
    private val userUseCase: UserUseCase
) {
    @PostMapping
    fun createUser(@RequestBody createUserSignUpRequest: UserSignUpRequest) =
        GetUserResponse(userUseCase.createUser(createUserSignUpRequest))
}
