package me.hyungil.gateway.controller.failback

import me.hyungil.gateway.controller.dto.ErrorResponse
import me.hyungil.gateway.enumeration.GATEWAY_TIME_OUT_MESSAGE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@RestController
class FailBackController {

    @PostMapping("/fallback")
    fun postFallback(exchange: ServerWebExchange) = Mono.just(ErrorResponse.of(GATEWAY_TIME_OUT_MESSAGE))
}