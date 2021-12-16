package me.hyungil.core.error.exception

class NotFoundRequestException(message: String) : RuntimeException(message) {
    constructor() : this("존재하지 않는 리소스입니다.")
}
