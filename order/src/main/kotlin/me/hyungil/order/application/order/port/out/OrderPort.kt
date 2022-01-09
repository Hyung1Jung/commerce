package me.hyungil.order.application.order.port.out

import me.hyungil.order.domain.order.Order

interface OrderPort {

    fun save(order: Order): Order
}
