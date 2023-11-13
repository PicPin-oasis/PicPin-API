package com.picpin.api.metaattribute.interfaces

import com.fasterxml.jackson.annotation.JsonProperty

data class GetMetaAttributesResponse(
    @JsonProperty("province_filters")
    val provinceFilters: List<StaticProvinceFilter>,
    @JsonProperty("marker_colors")
    val markerColors: List<StaticMarkerColor>,
    @JsonProperty("marker_color_filters")
    val markerFilters: List<StaticMarkerColorFilter>
)
