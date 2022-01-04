package me.hyungil.order.commom.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.kafka")
class KafkaProperties(val bootstrap_servers_config: String)