package me.hyungil.auth.application.auth.service

import me.hyungil.auth.application.auth.port.`in`.LoginRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "commerce-user", url = "\${feign.commerce-user.url}")
interface FeignUserRemoteService {

    @PostMapping("login")
    fun login(@RequestBody loginRequest: LoginRequest): String?

    @GetMapping("{id}")
    fun getUserInfo(@PathVariable id: Long): String?
}