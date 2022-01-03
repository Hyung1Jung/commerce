package me.hyungil.order.adapter.`in`.presentation

import me.hyungil.core.error.handler.GlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class OrderExceptionHandler : GlobalExceptionHandler