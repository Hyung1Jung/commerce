package me.hyungil.auth.commom.config

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtAuthenticationFilter(private val jwtProvider: JwtProvider) : GenericFilterBean() {

    companion object {
        private val log = LoggerFactory.getLogger(JwtProvider::class.java)
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {

        val token = jwtProvider.resolveToken(request as HttpServletRequest)

        log.info("[Verifying token]")
        log.info(request.requestURL.toString())

        if (token?.let { jwtProvider.validationToken(it) } == true) {
            val authentication: Authentication = jwtProvider.getAuthentication(token)

            SecurityContextHolder.getContext().authentication = authentication

        }

        filterChain.doFilter(request, response)
    }
}