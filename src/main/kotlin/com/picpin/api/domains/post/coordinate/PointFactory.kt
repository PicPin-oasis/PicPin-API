package com.picpin.api.domains.post.coordinate

import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader

object PointFactory {
    private val wktReader = WKTReader()

    fun create(x: String, y: String): Point {
        val point = (wktReader.read("Point($y $x)") as Point)
        point.srid = 4326

        return point
    }
}
