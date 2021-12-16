package me.hyungil.core.error.handler

import me.hyungil.core.error.dto.ErrorResponse
import me.hyungil.core.error.exception.ConflictRequestException
import me.hyungil.core.error.exception.NotFoundRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
interface GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException) =
        exception.bindingResult.fieldError?.defaultMessage?.let { ErrorResponse.of(it) }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictRequestException::class)
    fun handleConflictRequestException(exception: ConflictRequestException) =
        exception.message?.let { ErrorResponse.of(it) }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundRequestException::class)
    fun handleNotFoundException(exception: NotFoundRequestException) =
        exception.message?.let { ErrorResponse.of(it) }
}
