package com.picpin.api.album.domains

import com.picpin.api.verticals.domains.base.BaseTimeEntity
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
) : BaseTimeEntity() {
    companion object {
        fun fixture(
            title: String = "바다 파도와 해변",
            coverImageUrl: String = "https://media.istockphoto.com/id/1460655983/ko/%EC%82%AC%EC%A7%84/%EB%B0%94%EB%8B%A4-%ED%8C%8C%EB%8F%84%EC%99%80-%ED%95%B4%EB%B3%80-%EA%BC%AD%EB%8C%80%EA%B8%B0-%EC%A0%84%EB%A7%9D-%EC%9E%90%EC%97%B0-%EB%B0%B0%EA%B2%BD.jpg?s=2048x2048&w=is&k=20&c=d4jbV3hiWrA1ScceiUUYEP-VKDV_EtZxrigL6YsUrQ8=",
            ownerId: Long = 1,
            id: Long = 1
        ): Album = Album(title, coverImageUrl, ownerId, id)
    }
}
