package me.hyungil.core.error.dto

data class ErrorResponse(val message: String) {

    companion object {
        fun of(message: String): ErrorResponse {
            return ErrorResponse(message)
        }
    }
}
