package me.hyungil.user.application.user.port.`in`

import me.hyungil.user.domain.user.User

interface UserUseCase {

    fun createUser(userCreateRequest: UserRequest): User

    fun login(loginRequest: LoginRequest): User?
}
