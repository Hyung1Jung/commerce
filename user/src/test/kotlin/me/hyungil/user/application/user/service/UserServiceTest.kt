package me.hyungil.user.application.user.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import me.hyungil.core.error.exception.ConflictRequestException
import me.hyungil.core.error.exception.NotFoundRequestException
import me.hyungil.user.application.user.port.`in`.GetUserResponse
import me.hyungil.user.application.user.port.`in`.LoginRequest
import me.hyungil.user.application.user.port.`in`.UserRequest
import me.hyungil.user.application.user.port.out.UserPort
import me.hyungil.user.domain.user.User
import java.time.LocalDateTime

internal class UserServiceTest : BehaviorSpec() {

    private val userAdapter: UserPort = mockk(relaxed = true)
    private val userService = UserService(userAdapter)

    init {

        Given("유저를 생성을 요청할 때") {
            val userRequest = UserRequest("hyungil@gmail.com", "12345qwert@")

            `when`("이미 존재하는 이메일이라면") {
                every { userAdapter.findByEmail(userRequest.email) } answers {
                    User(
                        1,
                        userRequest.email,
                        userRequest.password,
                        LocalDateTime.now(),
                        null,
                        setOf("USER_ROLE")
                    )
                }

                val exception = shouldThrow<ConflictRequestException> { userService.createUser(userRequest) }

                Then("예외메시지를 출력하고 유저 생성에 실패한다,") {
                    exception.message shouldBe "이미 존재하는 리소스입니다."
                }
            }

            `when`("존재하는 이메일이 아니라면") {
                every { userAdapter.findByEmail(userRequest.email) } returns null

                Then("유저 생성에 성공한다") {
                    val user = User(
                        1,
                        userRequest.email,
                        userRequest.password,
                        LocalDateTime.now(),
                        null,
                        setOf("USER_ROLE")
                    )

                    every { userAdapter.save(user) } shouldNotBe null
                }
            }
        }

        Given("로그인 시") {
            val loginRequest = LoginRequest("hyungil@gmail.com", "12345qwert@")

            `when`("존재하지 않는 사용자라면") {
                every { userAdapter.findByEmail(loginRequest.email) } returns null

                val exception = shouldThrow<NotFoundRequestException> { userService.login(loginRequest) }

                Then("예외메시지를 출력하고 로그인에 실패한다.") {
                    exception.message shouldBe "존재하지 않는 리소스입니다."
                }
            }

            `when`("존재하는 사용자라면") {
                val user = User(
                    1,
                    loginRequest.email,
                    loginRequest.password,
                    LocalDateTime.now(),
                    null,
                    setOf("USER_ROLE")
                )
                every { userAdapter.findByEmail(loginRequest.email) } answers { user }

                Then("로그인에 성공한다.") {
                    every { userService.getUserInfo(user.id) } shouldNotBe null;
                }
            }
        }

        Given("사용자 정보를 조회할 때,") {
            val userId = 1L

            `when`("존재하지 않는 사용자라면") {
                every { userAdapter.findByIdOrNull(userId) } returns null

                val exception = shouldThrow<NotFoundRequestException> { userService.getUserInfo(userId) }

                Then("예외메시지를 출력하고 사용자 정보 조회에 실패한다.") {
                    exception.message shouldBe "존재하지 않는 리소스입니다."
                }
            }

            `when`("존재하는 사용자라면") {
                val user = User(
                    userId,
                    "user@gmail.com",
                    "QWMEN@#(@#KLMSD!@",
                    LocalDateTime.now(),
                    null,
                    setOf("USER_ROLE")
                )
                every { userAdapter.findByIdOrNull(userId) } answers { user }

                Then("사용자 정보 조회에 성공한다.") {
                    every { userService.getUserInfo(user.id) } shouldNotBe null
                }
            }
        }
    }
}
