package me.hyungil.product.adapter.out.infrastructure.persistence

import me.hyungil.product.commom.BaseTimeEntity
import me.hyungil.product.domain.product.Product
import javax.persistence.*

@Entity
@Table(name = "product")
class ProductEntity(

    @Column(nullable = false)
    val productId: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val productName: String,

    @Column(nullable = false)
    val unitPrice: Long,

    @Column(nullable = false)
    val stock: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

) : BaseTimeEntity() {

    constructor(product: Product) : this(
        product.productId,
        product.email,
        product.productName,
        product.unitPrice,
        product.stock,
        product.id
    )

    fun toProductDomain() = Product(productId, email, productName, unitPrice, stock, createdAt, updatedAt, id)
}