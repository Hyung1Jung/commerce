package me.hyungil.order.application.order.service

import me.hyungil.order.application.order.port.`in`.OrderRequest
import me.hyungil.order.application.order.port.`in`.OrderUseCase
import me.hyungil.order.application.order.port.out.OrderPort
import me.hyungil.order.domain.order.Order
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderAdapter: OrderPort,
    private val kafkaProducer: KafkaProducer
) : OrderUseCase {

    override fun createOrder(userId: Long, orderRequest: OrderRequest, email: String): Order {

        kafkaProducer.send("order-product-topic", orderRequest)

        return orderAdapter.save(orderRequest.toOrderDomain(userId, email))
    }
}
