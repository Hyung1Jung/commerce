package me.hyungil.user.application.user.service

import me.hyungil.core.error.exception.ConflictRequestException
import me.hyungil.core.error.exception.NotFoundRequestException
import me.hyungil.core.error.exception.UnauthorizedAccessRequestException
import me.hyungil.user.application.user.port.`in`.LoginRequest
import me.hyungil.user.application.user.port.`in`.UserRequest
import me.hyungil.user.application.user.port.`in`.UserUseCase
import me.hyungil.user.application.user.port.out.UserPort
import me.hyungil.user.domain.user.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(private val userAdapter: UserPort) : UserUseCase {

    override fun createUser(userCreateRequest: UserRequest): User {

        duplicationEmailCheck(userCreateRequest.email)

        return userAdapter.save(userCreateRequest.toUserDomain(BCryptPasswordEncoder().encode(userCreateRequest.password)))
    }

    override fun login(loginRequest: LoginRequest): User {

        val user = findByEmail(loginRequest.email)

        inValidPasswordCheck(loginRequest.password, user.password)

        return user
    }

    override fun getUserInfo(id: Long) = findById(id)

    private fun inValidPasswordCheck(userLoginPassword: String, userPassword: String) {

        if (!BCryptPasswordEncoder().matches(userLoginPassword, userPassword)) throw UnauthorizedAccessRequestException()
    }

    private fun duplicationEmailCheck(email: String) {

        userAdapter.findByEmail(email)?.let { throw ConflictRequestException() }
    }

    private fun findByEmail(email: String) = userAdapter.findByEmail(email) ?: throw NotFoundRequestException()

    private fun findById(id: Long) = userAdapter.findByIdOrNull(id) ?: throw NotFoundRequestException()
}
