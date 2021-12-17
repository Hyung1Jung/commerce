package me.hyungil.user.application.user.port.`in`

import me.hyungil.user.domain.user.User
import java.time.LocalDateTime

data class GetUserResponse(

    val id: Long,

    val email: String,

    val createdAt : LocalDateTime,

    val updatedAt : LocalDateTime?,

    val role: Set<String>

) {

    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt,
        role = user.roles
    )

}