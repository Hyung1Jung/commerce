package me.hyungil.product.application.product.port.`in`

interface KafkaUseCase {

    fun updateQty(kafkaMessage: String)
}