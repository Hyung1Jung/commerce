package me.hyungil.auth.application.auth.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import me.hyungil.auth.application.auth.port.`in`.GetTokenResponse
import me.hyungil.auth.application.auth.port.`in`.GetUserResponse
import me.hyungil.auth.application.auth.port.`in`.LoginRequest
import me.hyungil.auth.application.auth.port.`in`.TokenRequest
import me.hyungil.auth.commom.config.JwtProvider
import me.hyungil.core.error.exception.NotFoundRequestException
import me.hyungil.core.error.exception.UnauthorizedAccessRequestException
import org.springframework.data.redis.core.StringRedisTemplate

internal class AuthServiceTest : BehaviorSpec() {

    private val jwtProvider: JwtProvider = mockk(relaxed = true)
    private val feignUserRemoteService: FeignUserRemoteService = mockk(relaxed = true)
    private val redisTemplate: StringRedisTemplate = mockk(relaxed = true)

    private val authService = AuthService(jwtProvider, feignUserRemoteService, redisTemplate)

    init {

        Given("로그인 시") {
            val loginRequest = LoginRequest("hyungil@gmail.com", "12345qwert@")

            `when`("존재하지 않는 사용자라면") {
                every { feignUserRemoteService.login(loginRequest) } returns null

                val exception = shouldThrow<NotFoundRequestException> { authService.login(loginRequest) }

                Then("예외메시지를 출력하고 로그인에 실패한다.") {
                    exception.message shouldBe "존재하지 않는 리소스입니다."
                }
            }
        }

        Given("리프레시 토큰 재발급 시") {
            val tokenRequest = TokenRequest("YTUGIHOJLKMNJKBHGHFG", "JOIUHJGVFGDTRFYGUH")

            `when`("만료된 토큰이거나 요청한 리프레시 토큰이 기존의 리프레시 토큰과 일치하지 않는다면") {
                every { jwtProvider.validationToken(tokenRequest.refreshToken) } returns false

                val tokenRequestRefreshToken = "IHUOJKPBHJJNLKM"
                val existRefreshToken = "JOIUHJGVFGDTRFYGUH"
                tokenRequestRefreshToken shouldNotBe existRefreshToken

                val exception = shouldThrow<UnauthorizedAccessRequestException> { authService.reissue(tokenRequest) }

                Then("예외메시지를 출력하고 리프레시 토큰 재발급에 실패한다.") {
                    exception.message shouldBe "만료된 refreshToken error 입니다."
                }
            }

            `when`("요청한 리프레시 토큰이 기존의 리프레시 토큰이 정보가 일치한다면") {
                val existRefreshToken = "JOIUHJGVFGDTRFYGUH"
                tokenRequest.refreshToken shouldBe existRefreshToken

                Then("리프레시 토큰 재발급에 성공한다.") {
                    val createRefreshToken = "TFUGYIHOJLKNJHBVGJ"
                    existRefreshToken shouldNotBe createRefreshToken
                }
            }
        }
    }
}