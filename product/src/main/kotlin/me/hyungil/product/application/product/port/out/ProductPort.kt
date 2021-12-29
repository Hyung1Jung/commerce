package me.hyungil.product.application.product.port.out

import me.hyungil.product.domain.product.Product

interface ProductPort {

    fun save(product: Product): Product

    fun findAll(): List<Product>

    fun findByProductId(productId: String): Product?
}