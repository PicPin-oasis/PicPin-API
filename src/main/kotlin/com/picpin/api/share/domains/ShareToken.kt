package com.picpin.api.share.domains

import com.picpin.api.verticals.domains.base.BaseTimeEntity
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
        Index(name = "idx_share_token_owner_id", columnList = "owner_id"),
        Index(name = "idx_share_token_payload", columnList = "payload")
    ]
)
class ShareToken(
    @Column(nullable = false)
    val payload: String,
    @Column(nullable = false, name = "owner_id")
    val ownerId: Long,
    @Column(nullable = false, name = "album_id")
    val albumId: Long,
    @Column(nullable = false, name = "expired_at")
    val expiredAt: LocalDateTime,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity()
