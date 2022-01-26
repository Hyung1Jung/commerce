package me.hyungil.auth.commom.config

import io.jsonwebtoken.*
import io.jsonwebtoken.impl.Base64UrlCodec
import me.hyungil.auth.application.auth.port.`in`.GetTokenResponse
import me.hyungil.core.error.exception.UnauthorizedAccessRequestException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtProvider(

    @Value("spring.jwt.secret")
    private var secretKey: String,
    private val userDetailsService: UserDetailsService

) {
    private val ROLES: String = "roles"
    private val accessTokenValidMillisecond: Long = 60 * 60 * 1000L
    private val refreshTokenValidMillisecond: Long = 14 * 24 * 60 * 60 * 1000L

    companion object {
        private val log = LoggerFactory.getLogger(JwtProvider::class.java)
    }

    @PostConstruct
    private fun init() {
        secretKey = Base64UrlCodec.BASE64URL.encode(secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    fun createTokenDto(userPk: Long, roles: Set<String>, email: String): GetTokenResponse {

        val claims = Jwts.claims().setSubject(userPk.toString())
        claims[ROLES] = roles
        claims["email"] = email

        val now = Date()

        val accessToken = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + accessTokenValidMillisecond))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()

        val refreshToken = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setExpiration(Date(now.time + refreshTokenValidMillisecond))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()

        return GetTokenResponse(
            grantType = "Bearer",
            accessToken = "Bearer $accessToken",
            refreshToken = refreshToken,
            accessTokenExpireDate = accessTokenValidMillisecond
        )
    }

    fun getAuthentication(token: String): Authentication {

        val claims = parseClaims(token)

        claims[ROLES] ?: throw UnauthorizedAccessRequestException()

        val userDetails = userDetailsService.loadUserByUsername(claims.subject)

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun parseClaims(token: String) =
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }

    fun resolveToken(request: HttpServletRequest?) = request?.getHeader("X-AUTH-TOKEN")

    fun validationToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            log.error("잘못된 Jwt 서명입니다.")
        } catch (e: MalformedJwtException) {
            log.error("잘못된 Jwt 서명입니다.")
        } catch (e: ExpiredJwtException) {
            log.error("만료된 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            log.error("지원하지 않는 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            log.error("잘못된 토큰입니다.")
        }
        return false
    }
}