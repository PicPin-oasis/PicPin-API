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
    var albumId: Long?,
    @Column(nullable = false, name = "writer_id")
    val writerId: Long,
    @Column(nullable = false, length = 10)
    var title: String,
    @Column(nullable = false, length = 100)
    var memo: String,
    @Column(nullable = false, length = 7, name = "marker_hex_code")
    var markerHexCode: String,
    @Column(nullable = false, length = 50, name = "taken_photo_address")
    var takenPhotoAddress: String,
    @Column(nullable = false, length = 50, name = "taken_photo_date")
    var takenPhotoDate: LocalDate,
    @Column(nullable = false, length = 100, name = "post_coordinate_id")
    val postCoordinateId: Long,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity() {

    fun isOwner(writerId: Long): Boolean = this.writerId == writerId

    fun update(transientPost: TransientPost) {
        transientPost.albumId?.let { this.albumId = it }
        transientPost.title?.let { this.title = it }
        transientPost.memo?.let { this.memo = it }
        transientPost.markerHexCode?.let { this.markerHexCode = it }
        transientPost.takenPhotoAddress?.let { this.takenPhotoAddress = it }
        transientPost.takenPhotoDate?.let { this.takenPhotoDate = it }
    }
}

data class TransientPost(
    val id: Long,
    val albumId: Long?,
    val writerId: Long,
    val title: String?,
    val memo: String?,
    val markerHexCode: String?,
    val takenPhotoAddress: String?,
    val takenPhotoDate: LocalDate?,
)
