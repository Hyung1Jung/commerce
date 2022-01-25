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
    val email: String,

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
        id = order.id,
        productId = order.productId,
        email = order.email,
        quantity = order.quantity,
        unitPrice = order.unitPrice,
        totalPrice = order.quantity * order.unitPrice,
        userId = order.userId,
        orderId = order.orderId
    )

    fun toOrderDomain() = Order(
        id = id,
        email = email,
        productId = productId,
        quantity = quantity,
        unitPrice = unitPrice,
        totalPrice = totalPrice,
        userId = userId,
        orderId = orderId
    )
}
