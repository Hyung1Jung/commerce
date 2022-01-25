package me.hyungil.product.adapter.out.infrastructure.persistence

import me.hyungil.core.domain.BaseLongIdEntity
import me.hyungil.product.domain.product.Product
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "product")
class ProductEntity(

    id: Long,

    @Column(nullable = false)
    val productId: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val productName: String,

    @Column(nullable = false)
    val unitPrice: Long,

    @Column(nullable = false)
    val stock: Long

) : BaseLongIdEntity(id) {

    constructor(product: Product) : this(
        product.id,
        product.productId,
        product.email,
        product.productName,
        product.unitPrice,
        product.stock,
    )

    fun toProductDomain() = Product(id, productId, email, productName, unitPrice, stock, createdAt, updatedAt)
}