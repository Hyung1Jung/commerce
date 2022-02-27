package me.hyungil.product.domain.product

import java.time.LocalDateTime

class Product(

    val productId: String,

    val email: String,

    val productName: String,

    val unitPrice: Long,

    var stock: Long,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime? = null,

    val id: Long ?= null,
) {

    fun updateStock(qty: Int) {
        this.stock = this.stock - qty
    }
}