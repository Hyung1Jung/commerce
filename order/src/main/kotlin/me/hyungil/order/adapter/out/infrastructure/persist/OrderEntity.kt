package me.hyungil.order.adapter.out.infrastructure.persist

import me.hyungil.order.commom.BaseTimeEntity
import me.hyungil.order.domain.order.Order
import javax.persistence.*

@Entity
@Table(name = "order_item")
class OrderEntity(

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
    val orderId: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

) : BaseTimeEntity() {

    constructor(order: Order) : this(
        order.productId,
        order.email,
        order.quantity,
        order.unitPrice,
        order.quantity * order.unitPrice,
        order.userId,
        order.orderId,
        order.id,
    )

    fun toOrderDomain() = Order(
        email,
        productId,
        quantity,
        unitPrice,
        totalPrice,
        userId,
        orderId,
        createdAt,
        updatedAt,
        id
    )
}
