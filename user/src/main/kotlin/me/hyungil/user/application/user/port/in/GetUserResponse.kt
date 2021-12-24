package me.hyungil.user.application.user.port.`in`

import me.hyungil.user.domain.user.User

data class GetUserResponse(

    val id: Long,

    val email: String,

    val roles: Set<String>

) {

    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        roles = user.roles
    )
}