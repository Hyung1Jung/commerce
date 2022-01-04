package me.hyungil.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan("me.hyungil.order.commom.property")
class OrderApplication

fun main(args: Array<String>) {
    runApplication<OrderApplication>(*args)
}
