package me.hyungil.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan("me.hyungil.product.commom.property")
class ProductApplication

fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}
