package me.hyungil.user.application.user.service

import me.hyungil.core.error.exception.NotFoundRequestException
import me.hyungil.user.adapter.out.infrastructure.persistence.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userPk: String): UserDetails? =
        (userRepository.findByIdOrNull(userPk.toLong()) ?: throw NotFoundRequestException()) as UserDetails?
}