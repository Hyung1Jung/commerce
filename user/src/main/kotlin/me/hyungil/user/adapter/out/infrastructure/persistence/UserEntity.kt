package me.hyungil.user.adapter.out.infrastructure.persistence

import me.hyungil.core.domain.BaseLongIdEntity
import me.hyungil.user.domain.user.User
import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity(

    id: Long,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    val roles: Set<String> = hashSetOf()

) : BaseLongIdEntity(id) {

    constructor(user: User) : this(user.id, user.email, user.password, user.roles)

    fun toUserDomain() = User(id, email, password, roles)

}
