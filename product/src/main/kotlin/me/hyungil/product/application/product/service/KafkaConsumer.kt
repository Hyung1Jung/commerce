package me.hyungil.product.application.product.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import me.hyungil.product.application.product.port.`in`.KafkaUseCase
import me.hyungil.product.application.product.port.out.ProductPort
import me.hyungil.product.commom.property.KafkaProperties
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service


@Service
class KafkaConsumer(private val productAdapter: ProductPort) : KafkaUseCase {

    companion object {
        private val log = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    @KafkaListener(topics = ["order-product-topic"])
    override fun updateQty(kafkaMessage: String) {

        log.info("Kafka Message: ->$kafkaMessage")

        var map: Map<Any, Any> = HashMap()
        val mapper = ObjectMapper()

        try {
            map = mapper.readValue(kafkaMessage, object : TypeReference<Map<Any, Any>>() {})
        } catch (ex: JsonProcessingException) {
            ex.printStackTrace()
        }

        val product = productAdapter.findByProductId(map["productId"] as String)

        if (product != null) {
            product.updateStock(map["quantity"] as Int)
            productAdapter.save(product)
        }
    }
}
