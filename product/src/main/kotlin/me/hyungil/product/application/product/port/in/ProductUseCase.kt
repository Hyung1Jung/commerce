package me.hyungil.product.application.product.port.`in`

import me.hyungil.product.domain.product.Product

interface ProductUseCase {

    fun createProduct(productRequest: ProductRequest, email: String): Product

    fun getProducts(): List<Product>
}