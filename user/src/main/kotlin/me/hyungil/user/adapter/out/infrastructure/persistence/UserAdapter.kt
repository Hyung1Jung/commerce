package me.hyungil.user.adapter.out.infrastructure.persistence

import me.hyungil.user.application.user.port.out.UserPort
import me.hyungil.user.domain.user.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserAdapter(private val userRepository: UserRepository): UserPort {

    override fun save(user: User) = userRepository.save(UserEntity(user)).toUserDomain()

    override fun findByEmail(email: String) = userRepository.findByEmail(email)?.toUserDomain()

    override fun findByIdOrNull(id: Long) = userRepository.findByIdOrNull(id)?.toUserDomain()
}
