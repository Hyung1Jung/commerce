package me.hyungil.user.application.user.service

import me.hyungil.core.error.exception.ConflictRequestException
import me.hyungil.user.application.user.port.`in`.UserSignUpRequest
import me.hyungil.user.application.user.port.`in`.UserUseCase
import me.hyungil.user.application.user.port.out.UserPort
import me.hyungil.user.domain.user.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(private val userAdapter: UserPort) : UserUseCase {

    override fun createUser(userSignUpRequest: UserSignUpRequest): User {

        conflictRequestCheck(userSignUpRequest.email)

        return userAdapter.save(userSignUpRequest.toUserDomain(BCryptPasswordEncoder().encode(userSignUpRequest.password)))
    }

    private fun conflictRequestCheck(request: String) {

        if (userAdapter.existsByEmail(request)) throw ConflictRequestException()
    }
}
