package com.picpin.api.photo.domains.coordinate.models

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoMeta(
    @JsonProperty("total_count")
    val totalCount: Int,
    @JsonProperty("pageable_count")
    val pageableCount: Int,
    @JsonProperty("is_end")
    val isEnd: Boolean
)
