package me.hyungil.auth.application.auth.port.`in`

data class TokenRequest(

    val accessToken: String,

    val refreshToken: String
)
