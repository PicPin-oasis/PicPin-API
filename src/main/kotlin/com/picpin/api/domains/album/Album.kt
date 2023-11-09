package com.picpin.api.domains.album

import com.picpin.api.domains.base.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_album_owner_id", columnList = "owner_id")
    ]
)
class Album(
    @Column(nullable = false, length = 10)
    val title: String,
    @Column(nullable = false, length = 100, name = "cover_image_url")
    val coverImageUrl: String,
    @Column(nullable = false, name = "owner_id")
    val ownerId: Long,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity()
