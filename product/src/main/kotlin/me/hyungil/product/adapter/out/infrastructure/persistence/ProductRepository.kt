package me.hyungil.product.adapter.out.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Long> {
    fun findByProductId(productId: String): ProductEntity
}