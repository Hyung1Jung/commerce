package me.hyungil.gateway.config

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class GlobalFilter : AbstractGatewayFilterFactory<GlobalFilter.Config>(Config::class.java) {

    companion object {
        private val log = LoggerFactory.getLogger(GlobalFilter::class.java)
    }

    override fun apply(config: Config): GatewayFilter {

        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->

            log.info("GlobalFilter baseMessage>>>>>>" + config.baseMessage)

            if (config.isPreLogger) {
                log.info("GlobalFilter Start>>>>>>" + exchange.request)
            }

            chain.filter(exchange).then(Mono.fromRunnable {

                if (config.isPostLogger) {
                    log.info("GlobalFilter End>>>>>>" + exchange.response)
                }
            })
        }
    }

    data class Config(
        val baseMessage: String? = null,
        val isPreLogger: Boolean = false,
        val isPostLogger: Boolean = false,
    )
}