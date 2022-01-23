package me.hyungil.auth.domain

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUser(

    private val id: Long = 0,

    private val email: String,

    private val password: String,

    private val roles: Set<String> = hashSetOf()

) : UserDetails {

    override fun getAuthorities() = roles.map { role -> SimpleGrantedAuthority(role) }

    override fun getPassword() = password

    override fun getUsername() = this.id.toString()

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    fun getEmail() = email
}
