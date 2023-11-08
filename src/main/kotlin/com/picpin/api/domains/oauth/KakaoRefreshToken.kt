package com.picpin.api.domains.oauth

import com.picpin.api.domains.base.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(
    indexes = [
        Index(name = "idx_kakao_refresh_token_vendor_id", columnList = "vendor_id")
    ]
)
class KakaoRefreshToken(
    @Column(nullable = false, name = "vendor_id")
    val vendorId: Long,
    @Column(nullable = false, name = "account_id")
    val accountId: Long,
    @Column(nullable = false, name = "payload")
    val payload: String,
    @Column(nullable = false)
    val expireDateTime: LocalDateTime,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()
