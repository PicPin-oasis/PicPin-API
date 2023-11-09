package com.picpin.api.domains.photo

import com.picpin.api.domains.base.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Entity
@Table(
    indexes = [
        Index(name = "idx_photo_post_id", columnList = "post_id")
    ]
)
class Photo(
    @Column(nullable = false, name = "post_id")
    val postId: Long,
    @Column(nullable = false, name = "owner_id")
    val ownerId: Long,
    @Column(nullable = false, length = 100, name = "image_url")
    var imageUrl: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity()

interface PhotoRepository : JpaRepository<Photo, Long> {
    fun findAllByPostIdIn(postId: List<Long>): List<Photo>
}

@Service
class PhotoService(
    private val photoRepository: PhotoRepository
) {

    fun saveAll(photos: List<Photo>) {
        photoRepository.saveAll(photos)
    }

    fun deleteAll(photos: List<Photo>) {
        photoRepository.deleteAll(photos)
    }

    @Transactional(readOnly = true)
    fun findAllBy(postIds: List<Long>): List<Photo> = photoRepository.findAllByPostIdIn(postIds)
}
