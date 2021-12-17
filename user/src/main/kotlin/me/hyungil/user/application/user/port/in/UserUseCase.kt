package me.hyungil.user.application.user.port.`in`

import me.hyungil.user.domain.user.User

interface UserUseCase {

    fun createUser(userSignUpRequest: UserSignUpRequest): User
}
