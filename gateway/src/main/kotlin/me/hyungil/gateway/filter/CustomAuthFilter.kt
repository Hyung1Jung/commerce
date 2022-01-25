package me.hyungil.gateway.filter

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.impl.Base64UrlCodec
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import java.util.Objects
import java.util.function.Consumer
import javax.annotation.PostConstruct


@Component
class CustomAuthFilter(

    @Value("spring.jwt.secret")
    private var secretKey: String

) : AbstractGatewayFilterFactory<CustomAuthFilter.Config>(Config::class.java) {

    companion object {
        const val BEARER = "Bearer"
        const val EMAIL = "email"
    }

    @PostConstruct
    private fun init() {
        secretKey = Base64UrlCodec.BASE64URL.encode(secretKey.toByteArray(StandardCharsets.UTF_8))
    }

    override fun apply(config: Config): GatewayFilter {

        return (GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->

            val request = exchange.request

            if ((!request.headers.containsKey(HttpHeaders.AUTHORIZATION))) {
                return@GatewayFilter handleUnAuthorized(exchange)
            }

            val token = request.headers[HttpHeaders.AUTHORIZATION] as List<String>
            val tokenString = Objects.requireNonNull(token)[0]

            if (!tokenString.startsWith(BEARER)) {
                return@GatewayFilter handleUnAuthorized(exchange)
            }

            val claims = getClaims(tokenString.substring("$BEARER ".length))

            val httpHeaders =
                Consumer<HttpHeaders> { httpHeader -> httpHeader.set(EMAIL, claims[EMAIL].toString()) }

            val serverHttpRequest = exchange.request.mutate().headers(httpHeaders).build()

            exchange.mutate().request(serverHttpRequest).build()

            return@GatewayFilter chain.filter(exchange.mutate().request(request).build())

        })
    }

    private fun handleUnAuthorized(exchange: ServerWebExchange): Mono<Void> {

        val response: ServerHttpResponse = exchange.response

        response.statusCode = HttpStatus.UNAUTHORIZED

        return response.setComplete()
    }

    private fun getClaims(token: String) = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body

    class Config
}
