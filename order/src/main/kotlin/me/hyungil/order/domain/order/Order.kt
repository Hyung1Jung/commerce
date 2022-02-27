package me.hyungil.order.domain.order

import java.time.LocalDateTime

class Order(
    val email: String,

    val productId: String,

    val quantity: Long,

    val unitPrice: Long,

    val totalPrice: Long,

    val userId: Long,

    val orderId: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime? = null,

    val id: Long? = null,
)