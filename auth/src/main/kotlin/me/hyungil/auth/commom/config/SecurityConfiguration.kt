package me.hyungil.auth.commom.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfiguration(private val jwtProvider: JwtProvider) : WebSecurityConfigurerAdapter() {

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .cors()

            .and()

            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()

            .authorizeRequests()
            .antMatchers(HttpMethod.POST,  "/v1/auth/**").permitAll()
            .anyRequest().hasRole("USER")

            .and()
            .addFilterBefore(JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)
    }
}
