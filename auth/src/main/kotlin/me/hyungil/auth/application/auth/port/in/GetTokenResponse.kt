package me.hyungil.auth.application.auth.port.`in`

data class GetTokenResponse(

    val grantType: String,

    val accessToken: String,

    val refreshToken: String,

    val accessTokenExpireDate: Long
)