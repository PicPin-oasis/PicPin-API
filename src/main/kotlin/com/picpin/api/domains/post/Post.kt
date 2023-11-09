package com.picpin.api.domains.post

import com.picpin.api.domains.base.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(
    indexes = [
        Index(name = "idx_post_writer_id", columnList = "writer_id")
    ]
)
class Post(
    @Column(nullable = true, name = "album_id")
    val albumId: Long?,
    @Column(nullable = false, name = "writer_id")
    val writerId: Long,
    @Column(nullable = false, length = 10)
    val title: String,
    @Column(nullable = false, length = 100)
    val memo: String,
    @Column(nullable = false, length = 7, name = "marker_hex_code")
    val markerHexCode: String,
    @Column(nullable = false, length = 50, name = "taken_photo_address")
    val takenPhotoAddress: String,
    @Column(nullable = false, length = 50, name = "taken_photo_date")
    val takenPhotoDate: LocalDate,
    @Column(nullable = false, length = 100, name = "post_coordinate_id")
    val postCoordinateId: Long,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()
