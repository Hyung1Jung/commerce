package me.hyungil.auth.application.auth.port.out

import me.hyungil.auth.domain.RefreshToken

interface AuthPort {

    fun save(refreshToken: RefreshToken)

    fun findBySecretKey(id: Long): RefreshToken?
}