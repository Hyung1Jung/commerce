package me.hyungil.product.application.product.port.`in`

import me.hyungil.product.domain.product.Product
import javax.validation.constraints.NotBlank

data class ProductRequest(

    @field:NotBlank(message = "상품 아이디는 필수 입력 입니다.")
    val productId: String,

    @field:NotBlank(message = "상품 이름은 필수 입력 입니다.")
    val productName: String,

    val unitPrice: Long,

    val stock: Long
) {

    fun toProductDomain(email: String) =
        Product(productId = productId, email = email, productName = productName, unitPrice = unitPrice, stock = stock)
}
