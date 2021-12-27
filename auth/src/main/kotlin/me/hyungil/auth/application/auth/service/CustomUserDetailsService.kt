package me.hyungil.auth.application.auth.service

import com.google.gson.Gson
import me.hyungil.auth.domain.CustomUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val feignUserRemoteService: FeignUserRemoteService) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return Gson().fromJson(feignUserRemoteService.getUserInfo(username.toLong()), CustomUser::class.java)
    }
}
