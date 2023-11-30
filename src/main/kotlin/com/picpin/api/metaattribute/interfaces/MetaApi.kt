package com.picpin.api.metaattribute.interfaces

import com.picpin.api.metaattribute.interfaces.models.GetMetaAttributesResponse
import com.picpin.api.metaattribute.interfaces.models.staticMarkerColorFilters
import com.picpin.api.metaattribute.interfaces.models.staticMarkerColors
import com.picpin.api.metaattribute.interfaces.models.staticProvinceFilters
import com.picpin.api.verticals.interfaces.model.AccountId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MetaApi : MetaApiDocs {

    @GetMapping("/mata-attributes")
    override fun getMetaAttributes(@AccountId accountId: Long): ResponseEntity<GetMetaAttributesResponse> {
        val getMetaAttributesResponse = GetMetaAttributesResponse(
            provinceFilters = staticProvinceFilters,
            markerColors = staticMarkerColors,
            markerFilters = staticMarkerColorFilters
        )
        return ResponseEntity.ok(getMetaAttributesResponse)
    }
}

@Tag(name = "메타데이터 API")
interface MetaApiDocs {

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
    fun getMetaAttributes(@AccountId accountId: Long): ResponseEntity<GetMetaAttributesResponse>
}
