package me.hyungil.product.domain.product

import java.time.LocalDateTime

class Product(

    val id: Long = 0,

    val productId: String,

    val productName: String,

    val unitPrice: Long,

    var stock: Long,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime? = null,
) {

    fun updateStock(qty: Int) {
        this.stock = this.stock - qty
    }
}