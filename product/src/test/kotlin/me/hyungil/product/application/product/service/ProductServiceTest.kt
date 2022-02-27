package me.hyungil.product.application.product.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import me.hyungil.product.application.product.port.`in`.ProductRequest
import me.hyungil.product.application.product.port.out.ProductPort
import me.hyungil.product.domain.product.Product
import java.time.LocalDateTime

internal class ProductServiceTest : BehaviorSpec() {

    private val productAdapter: ProductPort = mockk(relaxed = true)
    private val productService = ProductService(productAdapter)

    init {

        Given("상품 생성을 요청할 때") {
            val productRequest = ProductRequest("ABCDE-001", "book", 25000, 100)

            `when`("상품 생성 요청 정보에 올바른 값을 입력했다면") {
                val product = productRequest.toProductDomain("user@gmail.com")

                Then("상품 생성에 성공한다") {
                    every { productAdapter.save(product) } shouldNotBe null
                }
            }
        }

        Given("모든 상품 리스트를 조회할 때") {

            `when`("상품 리스트가 존재하거나 존재하지 않는다면") {
                val product: MutableList<Product> = ArrayList()
                product.add(Product("ABCDE-001", "user@gmail.com", "book", 25000, 100, LocalDateTime.now(), null, 1))
                product.add(Product("ABCDE-002", "user@gmail.com", "phone", 100000, 100, LocalDateTime.now(), null, 2))

                every { productAdapter.findAll() } answers { product }

                Then("사용자 정보 조회에 성공한다.") {
                    every { productService.getProducts() } shouldNotBe null
                }
            }
        }
    }
}