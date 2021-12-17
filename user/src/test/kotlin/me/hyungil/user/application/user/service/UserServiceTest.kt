package me.hyungil.user.application.user.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import me.hyungil.core.error.exception.ConflictRequestException
import me.hyungil.user.application.user.port.`in`.UserSignUpRequest
import me.hyungil.user.application.user.port.out.UserPort
import me.hyungil.user.domain.user.User
import java.time.LocalDateTime

internal class UserServiceTest : BehaviorSpec() {

    private val userAdapter: UserPort = mockk(relaxed = true)
    private val userService = UserService(userAdapter)

    init {

        Given("유저를 생성을 요청할 때") {
            val userSignUpRequest = UserSignUpRequest("hyungil@gmail.com", "12345qwert@")

            `when`("이미 존재하는 이메일이라면") {
                every { userAdapter.existsByEmail(userSignUpRequest.email) } returns true
                val exception = shouldThrow<ConflictRequestException> { userService.createUser(userSignUpRequest) }

                Then("예외메시지를 출력하고 유저 생성에 실패한다,") {
                    exception.message shouldBe "이미 존재하는 리소스입니다."
                }
            }

            `when`("존재하는 이메일이 아니라면") {
                every { userAdapter.existsByEmail(userSignUpRequest.email) } returns false

                Then("유저 생성에 성공한다") {
                    val user = User(
                        1,
                        userSignUpRequest.email,
                        userSignUpRequest.password,
                        LocalDateTime.now(),
                        null,
                        setOf("USER_ROLE")
                    )

                    every { userAdapter.save(user) } shouldNotBe null
                    every { userService.createUser(userSignUpRequest) } shouldNotBe null
                }
            }
        }
    }
}
