package me.hyungil.user.adapter.out.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun existsByEmail(email: String) : Boolean
}
