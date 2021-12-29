package me.hyungil.user.adapter.`in`.presentation.v1

import me.hyungil.user.application.user.port.`in`.GetUserResponse
import me.hyungil.user.application.user.port.`in`.LoginRequest
import me.hyungil.user.application.user.port.`in`.UserRequest
import me.hyungil.user.application.user.port.`in`.UserUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/users")
class UserController(
    private val userUseCase: UserUseCase
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userCreateRequest: UserRequest) =
        GetUserResponse(userUseCase.createUser(userCreateRequest))

    @PostMapping("login")
    fun login(@RequestBody loginRequest: LoginRequest) = userUseCase.login(loginRequest)?.let { GetUserResponse(it) }

    @GetMapping("{id}")
    fun getUserInfo(@PathVariable id: Long) = userUseCase.getUserInfo(id)?.let { GetUserResponse(it) }
}
