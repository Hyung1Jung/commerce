package me.hyungil.order.adapter.`in`.presentation.v1

import me.hyungil.order.application.order.port.`in`.GetOrderResponse
import me.hyungil.order.application.order.port.`in`.OrderRequest
import me.hyungil.order.application.order.port.`in`.OrderUseCase
import me.hyungil.order.application.order.service.KafkaProducer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/orders")
class OrderApi(
    private val orderUseCase: OrderUseCase,
    private val kafkaProducer: KafkaProducer
) {

    @PostMapping("{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@PathVariable("userId") userId: Long, @RequestBody orderRequest: OrderRequest) =
        GetOrderResponse(orderUseCase.createOrder(userId, orderRequest))
}
