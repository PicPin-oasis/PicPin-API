package com.picpin.api.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domains.base.StaticMarkerColor
import com.picpin.api.domains.base.StaticMarkerColorFilter
import com.picpin.api.domains.base.StaticProvinceFilter

data class GetMetaAttributesResponse(
    @JsonProperty("province_filters")
    val provinceFilters: List<StaticProvinceFilter>,
    @JsonProperty("marker_colors")
    val markerColors: List<StaticMarkerColor>,
    @JsonProperty("marker_color_filters")
    val markerFilters: List<StaticMarkerColorFilter>
)
