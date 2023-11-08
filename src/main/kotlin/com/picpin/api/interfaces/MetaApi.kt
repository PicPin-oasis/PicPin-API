package com.picpin.api.interfaces

import com.picpin.api.domains.base.createStaticProvinceFilters
import com.picpin.api.domains.base.createStaticMarkerColors
import com.picpin.api.domains.base.createStaticMarkerColorFilters
import com.picpin.api.interfaces.model.GetMetaAttributesResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MetaApi {

    @GetMapping("/mata-attributes")
    fun getMetaAttributes(): ResponseEntity<GetMetaAttributesResponse> {
        val getMetaAttributesResponse = GetMetaAttributesResponse(
            provinceFilters = createStaticProvinceFilters(),
            markerColors = createStaticMarkerColors(),
            markerFilters = createStaticMarkerColorFilters()
        )
        return ResponseEntity.ok(getMetaAttributesResponse)
    }
}
