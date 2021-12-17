package me.hyungil.user.application.user.port.out

import me.hyungil.user.domain.user.User

interface UserPort {

    fun save(user: User) : User

    fun existsByEmail(email: String) : Boolean
}
