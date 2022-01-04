package me.hyungil.order.application.order.port.`in`

import me.hyungil.order.domain.order.Order

interface OrderUseCase {

    fun createOrder(userId: Long, orderRequest: OrderRequest): Order
}
