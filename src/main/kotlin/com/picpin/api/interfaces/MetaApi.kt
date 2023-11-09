package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.staticProvinceFilters
import com.picpin.api.interfaces.model.GetMetaAttributesResponse
import com.picpin.api.interfaces.model.staticMarkerColorFilters
import com.picpin.api.interfaces.model.staticMarkerColors
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MetaApi {

    @GetMapping("/mata-attributes")
    fun getMetaAttributes(): ResponseEntity<GetMetaAttributesResponse> {
        val getMetaAttributesResponse = GetMetaAttributesResponse(
            provinceFilters = staticProvinceFilters,
            markerColors = staticMarkerColors,
            markerFilters = staticMarkerColorFilters
        )
        return ResponseEntity.ok(getMetaAttributesResponse)
    }
}
