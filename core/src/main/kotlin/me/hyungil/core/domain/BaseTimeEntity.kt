package me.hyungil.core.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseTimeEntity {

    @CreatedDate
    @Column(nullable = false)
    private val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    private var updatedAt: LocalDateTime? = null

}