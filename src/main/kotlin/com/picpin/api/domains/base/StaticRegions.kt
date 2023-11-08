package com.picpin.api.domains.base

import com.fasterxml.jackson.annotation.JsonProperty

val staticProvinceFilters =
    ProvinceCode.values().map { StaticProvinceFilter(it.id, it.code, it.rootName) }

enum class ProvinceCode(val id: Int, val code: Int, val rootName: String) {
    SEOUL(1, 11, "서울"),
    BUSAN(2, 26, "부산"),
    DAEGU(3, 27, "대구"),
    INCHEON(4, 28, "인천"),
    GWANGJU(5, 29, "광주"),
    DAEJEON(6, 30, "대전"),
    ULSAN(7, 31, "울산"),
    GYEONGGIDO(7, 41, "경기"),
    GANGWONDO(8, 42, "강원"),
    CHUNGCHEONGBUKDO(9, 43, "충북"),
    CHUNGCHEONGNAMDO(10, 44, "충남"),
    JEOLLABUKDO(11, 45, "전북"),
    JEOLLANAMDO(12, 46, "전남"),
    GYEONGSANGBUKDO(13, 47, "경북"),
    GYEONGSANGNAMDO(14, 48, "경남"),
    JEJUDO(15, 50, "제주"),
    SEJONG(16, 36, "세종");
}

data class StaticProvinceFilter(
    val id: Int,
    @JsonProperty("province_code")
    val provinceCode: Int,
    @JsonProperty("region_root_name")
    val regionRootName: String
)
