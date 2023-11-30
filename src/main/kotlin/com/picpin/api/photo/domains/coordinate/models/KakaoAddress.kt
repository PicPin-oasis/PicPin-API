package com.picpin.api.photo.domains.coordinate.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.metaattribute.interfaces.models.ProvinceCode

data class KakaoAddress(
    @JsonProperty("b_code")
    val bCode: String
) {

    fun getProvinceId(): ProvinceCode = ProvinceCode.findByCode(bCode.substring(0, 2).toInt())!!

    companion object {
        fun empty(): KakaoAddress = KakaoAddress("45")
    }
}
