package me.hyungil.core.error.exception

class UnauthorizedAccessRequestException(message: String) : RuntimeException(message) {
    constructor() : this("인증에 실패했습니다.")
}