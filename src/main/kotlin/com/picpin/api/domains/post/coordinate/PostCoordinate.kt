package com.picpin.api.domains.post.coordinate

import com.picpin.api.domains.base.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.locationtech.jts.geom.Point

@Entity
@Table(
    indexes = [
        Index(name = "idx_post_account_id_province_id", columnList = "account_id, province_id"),
        Index(name = "idx_post_coordinate_point", columnList = "point")
    ]
)
class PostCoordinate(
    @Column(nullable = false, length = 100, name = "account_id")
    val accountId: Long,
    @Column(nullable = false, length = 100, name = "province_id")
    val provinceId: Int,
    @Column(nullable = false, columnDefinition = "POINT SRID 4326")
    val point: Point,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()
