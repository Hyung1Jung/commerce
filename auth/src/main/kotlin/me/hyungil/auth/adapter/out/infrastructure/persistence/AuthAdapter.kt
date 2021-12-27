package me.hyungil.auth.adapter.out.infrastructure.persistence

import me.hyungil.auth.application.auth.port.out.AuthPort
import me.hyungil.auth.domain.RefreshToken
import org.springframework.stereotype.Repository

@Repository
class AuthAdapter(private val refreshTokenRepository: RefreshTokenRepository) : AuthPort {

    override fun save(refreshToken: RefreshToken) {
        refreshTokenRepository.save(RefreshTokenEntity(refreshToken)).toRefreshTokenDomain()
    }

    override fun findBySecretKey(id: Long) = refreshTokenRepository.findBySecretKey(id)?.toRefreshTokenDomain()
}
