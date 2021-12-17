package me.hyungil.core.error.exception

class ConflictRequestException(message: String) : RuntimeException(message) {
    constructor() : this("이미 존재하는 리소스입니다.")
}
