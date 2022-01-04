package me.hyungil.order.application.order.port.`in`

import me.hyungil.order.domain.order.Order

data class GetOrderResponse(

    val productId: String,

    val quantity: Long,

    val unitPrice: Long,

    val totalPrice: Long,

    val orderId: String
) {

    constructor(order: Order) : this(
        productId = order.productId,
        quantity = order.quantity,
        unitPrice = order.unitPrice,
        totalPrice = order.quantity * order.unitPrice,
        orderId = order.orderId
    )
}
