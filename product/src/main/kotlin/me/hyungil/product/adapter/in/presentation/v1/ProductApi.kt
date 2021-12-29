package me.hyungil.product.adapter.`in`.presentation.v1

import me.hyungil.product.application.product.port.`in`.GetProductResponse
import me.hyungil.product.application.product.port.`in`.ProductRequest
import me.hyungil.product.application.product.port.`in`.ProductUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("v1/products")
class ProductApi(
    private val productUseCase: ProductUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@Valid @RequestBody productRequest: ProductRequest) =
        GetProductResponse(productUseCase.createProduct(productRequest))

    @GetMapping
    fun getProducts() = productUseCase.getProducts().map { GetProductResponse(it) }
}