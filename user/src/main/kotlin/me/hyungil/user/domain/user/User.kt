package me.hyungil.user.domain.user

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

class User(

    val id: Long = 0,

    val email: String,

    private val password: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime? = null,

    val roles: Set<String> = hashSetOf()

) : UserDetails {

    override fun getAuthorities() = roles.map { role -> SimpleGrantedAuthority(role) }

    override fun getPassword() = password

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
