package me.hyungil.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class CoreApplication

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
