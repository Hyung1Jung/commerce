package me.hyungil.user.application.user.port.`in`

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class LoginRequest(

    @field:Email(message = "올바른 이메일 주소를 입력해주세요.")
    @field:NotBlank(message = "이메일은 필수 입력 입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수 입력 입니다.")
    @field:Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    val password: String
)