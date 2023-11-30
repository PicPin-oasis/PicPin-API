package com.picpin.api.photo.domains.coordinate.models

import com.picpin.api.metaattribute.interfaces.models.ProvinceCode
import org.locationtech.jts.geom.Point

data class KakaoLocalCoordinate(
    val provinceCode: ProvinceCode,
    val coordinate: Point
)
