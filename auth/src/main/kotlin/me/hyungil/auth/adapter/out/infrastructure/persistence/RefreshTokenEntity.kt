package me.hyungil.auth.adapter.out.infrastructure.persistence

import me.hyungil.auth.domain.RefreshToken
import me.hyungil.core.domain.BaseLongIdEntity
import javax.persistence.*

@Entity
@Table(name = "refresh_token")
class RefreshTokenEntity(

    id: Long,

    @Column(nullable = false)
    val secretKey: Long,

    @Column(nullable = false)
    var token: String

) : BaseLongIdEntity(id) {

    constructor(refreshToken: RefreshToken) : this(refreshToken.id, refreshToken.secretKey, refreshToken.token)

    fun toRefreshTokenDomain() = RefreshToken(id, secretKey, token, createdAt, updatedAt)
}
