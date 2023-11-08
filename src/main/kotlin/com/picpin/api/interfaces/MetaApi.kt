package com.picpin.api.interfaces

import com.picpin.api.domains.base.staticProvinceFilters
import com.picpin.api.domains.base.staticMarkerColors
import com.picpin.api.domains.base.staticMarkerColorFilters
import com.picpin.api.interfaces.model.GetMetaAttributesResponse
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
