package me.hyungil.order.application.order.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import me.hyungil.order.application.order.port.`in`.OrderRequest
import me.hyungil.order.application.order.port.out.OrderPort

internal class OrderServiceTest : BehaviorSpec() {

    private val orderAdapter: OrderPort = mockk(relaxed = true)

    init {

        Given("상품 주문을 요청할 때") {
            val orderRequest = OrderRequest("ABCDE-001", 10, 25000)

            `when`("상품 주문 요청 정보에 올바른 값을 입력했다면") {
                val order = orderRequest.toOrderDomain(1, "user@gmail.com")

                Then("주문에 성공한다") {
                    every { orderAdapter.save(order) } shouldNotBe null
                }
            }
        }

    }
}