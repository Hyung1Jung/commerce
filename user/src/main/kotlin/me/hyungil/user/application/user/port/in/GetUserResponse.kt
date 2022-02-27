package me.hyungil.user.application.user.port.`in`

import me.hyungil.user.domain.user.User

data class GetUserResponse(

    val email: String,

    val roles: Set<String>,

    val id: Long?

) {
    constructor(user: User) : this(
        user.email,
        user.roles,
        user.id
    )
}