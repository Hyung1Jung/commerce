package me.hyungil.auth.application.auth.port.`in`

data class GetUserResponse(

    val id: Long,

    val email: String,

    val roles: Set<String>
)

