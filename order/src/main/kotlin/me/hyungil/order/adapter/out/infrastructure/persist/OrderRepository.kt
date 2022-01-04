package me.hyungil.order.adapter.out.infrastructure.persist

import me.hyungil.order.application.order.port.`in`.OrderRequest
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, Long>