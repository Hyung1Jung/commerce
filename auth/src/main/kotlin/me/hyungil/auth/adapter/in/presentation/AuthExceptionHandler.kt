package me.hyungil.auth.adapter.`in`.presentation

import me.hyungil.core.error.handler.GlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthExceptionHandler: GlobalExceptionHandler