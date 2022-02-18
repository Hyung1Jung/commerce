package me.hyungil.gateway.controller.dto

data class ErrorResponse(val message: String) {

    companion object {
        fun of(message: String) = ErrorResponse(message)
    }
}