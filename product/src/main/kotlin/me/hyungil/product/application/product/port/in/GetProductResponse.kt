package me.hyungil.product.application.product.port.`in`

import me.hyungil.product.domain.product.Product
import java.time.LocalDateTime

data class GetProductResponse(
    val id: Long,

    val productId: String,

    val productName: String,

    val unitPrice: Long,

    val stock: Long,

    val createdAt: LocalDateTime,

    var updatedAt: LocalDateTime?
) {

    constructor(product: Product) : this(
        id = product.id,
        productId = product.productId,
        productName = product.productName,
        unitPrice = product.unitPrice,
        stock = product.stock,
        createdAt = product.createdAt,
        updatedAt = product.updatedAt
    )
}
