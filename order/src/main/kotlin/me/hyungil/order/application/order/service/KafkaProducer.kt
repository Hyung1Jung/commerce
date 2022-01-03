package me.hyungil.order.application.order.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import me.hyungil.order.application.order.port.`in`.OrderRequest
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    companion object {
        private val log = LoggerFactory.getLogger(KafkaProducer::class.java)
    }

    fun send(topic: String, orderRequest: OrderRequest): OrderRequest {

        val mapper = ObjectMapper()
        var jsonInString = ""

        try {
            jsonInString = mapper.writeValueAsString(orderRequest)
        } catch (ex: JsonProcessingException) {
            ex.printStackTrace()
        }

        kafkaTemplate.send(topic, jsonInString)

        log.info("Kafka Producer sent data from the Order microservice: $orderRequest")

        return orderRequest
    }
}