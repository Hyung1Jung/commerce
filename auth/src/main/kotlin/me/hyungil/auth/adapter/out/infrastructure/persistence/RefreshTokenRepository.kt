package me.hyungil.auth.adapter.out.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, String> {
    fun findBySecretKey(key: Long)
}
