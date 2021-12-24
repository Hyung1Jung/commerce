package me.hyungil.auth.application.auth.port.`in`

data class TokenRequest(

    val accessToke: String,

    val refreshToken: String

)