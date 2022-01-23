package me.hyungil.gateway.filter

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.impl.Base64UrlCodec
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import java.util.*
import javax.annotation.PostConstruct

@Component
class CustomAuthFilter(

    @Value("spring.jwt.secret")
    private var secretKey: String

) : AbstractGatewayFilterFactory<CustomAuthFilter.Config>(Config::class.java) {

    companion object {
        const val TOKEN = "token"
        const val BEARER = "Bearer"
        const val EMAIL = "email"
    }

    @PostConstruct
    private fun init() {
        secretKey = Base64UrlCodec.BASE64URL.encode(secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    override fun apply(config: Config): GatewayFilter {

        return (GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->

            val request: ServerHttpRequest = exchange.request
            val response: ServerHttpResponse = exchange.response

            if ((!request.headers.containsKey(TOKEN))) {
                return@GatewayFilter handleUnAuthorized(exchange)
            }

            val token: List<String> = request.headers[TOKEN] as List<String>
            val tokenString: String = Objects.requireNonNull(token)[0]

            if (!tokenString.startsWith(BEARER)) {
                return@GatewayFilter handleUnAuthorized(exchange)
            }

            val claims: Claims = getClaims(tokenString.substring("$BEARER ".length))

            response.headers.set(EMAIL, claims[EMAIL].toString())

            chain.filter(exchange)

        })
    }

    private fun handleUnAuthorized(exchange: ServerWebExchange): Mono<Void> {

        val response: ServerHttpResponse = exchange.response

        response.statusCode = HttpStatus.UNAUTHORIZED

        return response.setComplete()
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
    }

    class Config
}
