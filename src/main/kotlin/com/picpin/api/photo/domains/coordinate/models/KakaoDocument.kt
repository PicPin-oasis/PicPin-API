package com.picpin.api.photo.domains.coordinate.models

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoDocument(
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("y")
    val latitude: Double,
    @JsonProperty("x")
    val longitude: Double,
    val address: KakaoAddress
) {

    companion object {
        fun empty(address: String): KakaoDocument = KakaoDocument(address, 0.0, 0.0, KakaoAddress.empty())
    }
}
