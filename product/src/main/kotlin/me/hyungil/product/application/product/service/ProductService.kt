package me.hyungil.product.application.product.service

import me.hyungil.product.application.product.port.`in`.ProductRequest
import me.hyungil.product.application.product.port.`in`.ProductUseCase
import me.hyungil.product.application.product.port.out.ProductPort
import org.springframework.stereotype.Service

@Service
class ProductService(private val productAdapter: ProductPort) : ProductUseCase {

    override fun createProduct(productRequest: ProductRequest, email: String) =
        productAdapter.save(productRequest.toProductDomain(email))

    override fun getProducts() = productAdapter.findAll()
}
