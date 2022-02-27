package me.hyungil.user.adapter.out.infrastructure.persistence

import me.hyungil.user.commom.BaseTimeEntity
import me.hyungil.user.domain.user.User
import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity(

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    val roles: Set<String> = hashSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

) : BaseTimeEntity() {

    constructor(user: User) : this(user.email, user.password, user.roles, user.id)

    fun toUserDomain() = User(email, password, createdAt, updatedAt, roles, id)
}
