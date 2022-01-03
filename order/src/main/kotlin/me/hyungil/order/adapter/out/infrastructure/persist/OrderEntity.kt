package me.hyungil.order.adapter.out.infrastructure.persist

import me.hyungil.core.domain.BaseLongIdEntity
import me.hyungil.order.domain.order.Order
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "order_item")
class OrderEntity(

    id: Long,

    @Column(nullable = false)
    val productId: String,

    @Column(nullable = false)
    val quantity: Long,

    @Column(nullable = false)
    val unitPrice: Long,

    @Column(nullable = false)
    val totalPrice: Long,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val orderId: String

) : BaseLongIdEntity(id) {

    constructor(order: Order) : this(
        order.id,
        order.productId,
        order.quantity,
        order.unitPrice,
        order.userId,
        order.totalPrice,
        order.orderId
    )

    fun toOrderDomain() = Order(id, productId, quantity, unitPrice, totalPrice, userId, orderId)
}