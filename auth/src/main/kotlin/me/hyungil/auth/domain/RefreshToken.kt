package me.hyungil.auth.domain

import java.time.LocalDateTime

class RefreshToken(

    val id: Long = 0,

    val secretKey: Long,

    var token: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime? = null
)
