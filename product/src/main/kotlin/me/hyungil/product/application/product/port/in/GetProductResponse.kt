package me.hyungil.product.application.product.port.`in`

import me.hyungil.product.domain.product.Product
import java.time.LocalDateTime

data class GetProductResponse(

    val productId: String,

    val email: String,

    val productName: String,

    val unitPrice: Long,

    val stock: Long,

    val createdAt: LocalDateTime,

    var updatedAt: LocalDateTime?,

    val id: Long?,
) {

    constructor(product: Product) : this(
        product.productId,
        product.email,
        product.productName,
        product.unitPrice,
        product.stock,
        product.createdAt,
        product.updatedAt,
        product.id,
    )
}
