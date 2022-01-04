package me.hyungil.order.application.order.port.`in`

import me.hyungil.order.domain.order.Order
import java.util.*


data class OrderRequest(

    val productId: String,

    val quantity: Long,

    val unitPrice: Long
) {

    fun toOrderDomain(userId: Long) =
        Order(
            productId = productId,
            quantity = quantity,
            unitPrice = unitPrice,
            totalPrice = quantity * unitPrice,
            userId = userId,
            orderId = UUID.randomUUID().toString()
        )
}
