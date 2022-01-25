package me.hyungil.product.adapter.`in`.presentation.v1

import me.hyungil.product.application.product.port.`in`.GetProductResponse
import me.hyungil.product.application.product.port.`in`.ProductRequest
import me.hyungil.product.application.product.port.`in`.ProductUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("v1/products")
class ProductApi(
    private val productUseCase: ProductUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(
        @Valid @RequestBody productRequest: ProductRequest,
        @RequestHeader("email") email: String
    ): GetProductResponse = GetProductResponse(productUseCase.createProduct(productRequest, email))


    @GetMapping
    fun getProducts() = productUseCase.getProducts().map { GetProductResponse(it) }
}
