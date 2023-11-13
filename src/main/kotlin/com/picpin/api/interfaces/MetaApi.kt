package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.GetMetaAttributesResponse
import com.picpin.api.interfaces.model.staticMarkerColorFilters
import com.picpin.api.interfaces.model.staticMarkerColors
import com.picpin.api.interfaces.model.staticProvinceFilters
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "메타데이터 API")
@RestController
class MetaApi {

    @Operation(
        method = "GET",
        summary = "메타데이터 조회 (마커 색상, 마커 색상 필터, 지역 필터)",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            )
        ]
    )
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
