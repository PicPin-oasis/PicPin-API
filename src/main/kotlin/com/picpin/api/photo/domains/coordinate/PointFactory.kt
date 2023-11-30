package com.picpin.api.photo.domains.coordinate

import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader

object PointFactory {
    private val wktReader = WKTReader()
    private const val ESPG_4326 = 4326

    fun create(longitude: String, latitude: String): Point =
        (wktReader.read("Point($latitude $longitude)") as Point).also {
            it.srid = ESPG_4326
        }
}
