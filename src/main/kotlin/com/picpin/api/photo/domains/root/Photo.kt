package com.picpin.api.photo.domains.root

import com.picpin.api.metaattribute.interfaces.models.ProvinceCode
import com.picpin.api.photo.domains.coordinate.PointFactory
import com.picpin.api.verticals.domains.base.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.locationtech.jts.geom.Point
import java.time.LocalDate

@Entity
@Table(
    indexes = [
        Index(name = "idx_photo_album_id", columnList = "album_id")
    ]
)
class Photo(
    @Column(nullable = true, name = "album_id")
    var albumId: Long?,
    @Column(nullable = false, name = "owner_id")
    val ownerId: Long,
    @Column(nullable = false, length = 10, name = "place_name")
    var placeName: String,
    @Column(nullable = true, length = 300)
    var memo: String?,
    @Column(nullable = false, length = 100, name = "image_url")
    var imageUrl: String,
    @Column(nullable = false, length = 100, name = "province_id")
    var provinceId: Int,
    @Column(nullable = false, columnDefinition = "POINT SRID 4326")
    var coordinate: Point,
    @Column(nullable = false, length = 100, name = "taken_photo_address")
    var takenPhotoAddress: String,
    @Column(nullable = false, name = "taken_photo_date")
    var takenPhotoDate: LocalDate,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity() {

    fun isOwner(ownerId: Long): Boolean = this.ownerId == ownerId

    fun update(photo: Photo) {
        this.albumId = photo.albumId
        this.placeName = photo.placeName
        this.memo = photo.memo
        this.provinceId = photo.provinceId
        this.coordinate = photo.coordinate
        this.takenPhotoAddress = photo.takenPhotoAddress
        this.takenPhotoDate = photo.takenPhotoDate
    }

    fun unlinkAlbum() {
        this.albumId = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (javaClass != other?.javaClass) {
            return false
        }

        other as Photo

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Photo(albumId=$albumId, ownerId=$ownerId, placeName='$placeName', memo='$memo', imageUrl='$imageUrl', provinceId=$provinceId, coordinate=$coordinate, takenPhotoAddress='$takenPhotoAddress', takenPhotoDate=$takenPhotoDate, id=$id)"
    }

    companion object {
        fun fixture(
            albumId: Long = 1,
            ownerId: Long = 1,
            placeName: String = "some place",
            memo: String = "some memo",
            imageUrl: String = "",
            provinceId: Int = ProvinceCode.SEOUL.id,
            coordinate: Point = PointFactory.create("30", "30"),
            takenPhotoAddress: String = "Seoul",
            takenPhotoDate: LocalDate = LocalDate.now(),
            id: Long = 1
        ): Photo =
            Photo(
                albumId = albumId,
                ownerId = ownerId,
                placeName = placeName,
                memo = memo,
                imageUrl = imageUrl,
                provinceId = provinceId,
                coordinate = coordinate,
                takenPhotoAddress = takenPhotoAddress,
                takenPhotoDate = takenPhotoDate,
                id = id
            )
    }
}
