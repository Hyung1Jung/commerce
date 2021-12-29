package me.hyungil.product.adapter.out.infrastructure.persistence

import me.hyungil.product.application.product.port.out.ProductPort
import me.hyungil.product.domain.product.Product
import org.springframework.stereotype.Repository

@Repository
class ProductAdapter(private val productRepository: ProductRepository) : ProductPort {

    override fun save(product: Product) = productRepository.save(ProductEntity(product)).toProductDomain()

    override fun findAll(): List<Product> = productRepository.findAll().map { it.toProductDomain() }

    override fun findByProductId(productId: String): Product? = productRepository.findByProductId(productId).toProductDomain()
}