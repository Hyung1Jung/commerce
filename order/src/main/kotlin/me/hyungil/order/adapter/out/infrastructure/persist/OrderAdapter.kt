package me.hyungil.order.adapter.out.infrastructure.persist

import me.hyungil.order.application.order.port.out.OrderPort
import me.hyungil.order.domain.order.Order
import org.springframework.stereotype.Repository

@Repository
class OrderAdapter(private val orderRepository: OrderRepository): OrderPort {

    override fun save(order: Order) = orderRepository.save(OrderEntity(order)).toOrderDomain()
}
