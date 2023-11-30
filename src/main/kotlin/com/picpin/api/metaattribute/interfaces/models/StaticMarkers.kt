package com.picpin.api.metaattribute.interfaces.models

import com.fasterxml.jackson.annotation.JsonProperty

val staticMarkerColors =
    MarkerColorCode.values().map { StaticMarkerColor(it.id, it.enableHexCode) }

val staticMarkerColorFilters =
    MarkerColorCode.values()
        .map { StaticMarkerColorFilter(it.id, it.enableHexCode, it.disableHexCode, false) }

enum class MarkerColorCode(val id: Int, val enableHexCode: String, val disableHexCode: String) {
    RED(1, "#FF0000", "#FFD9D9"),
    ORANGE(2, "#FF9901", "#FFF0D9"),
    YELLOW(3, "#FFE500", "#FFFBD9"),
    GREEN(4, "#38D12B", "#E1F8DF"),
    BLUE(5, "#359EFF", "#E1F0FF"),
    NAVY_BLUE(6, "#180399", "#DCD9F0"),
    PURPLE(7, "#B426F6", "#F4DEFE");

    companion object {
        fun findBy(markerColorId: Int): MarkerColorCode? = values()
            .find { it.id == markerColorId }
    }
}

data class StaticMarkerColorFilter(
    val id: Int,
    @JsonProperty("enable_hex_code")
    val enableHexCode: String,
    @JsonProperty("disable_hex_code")
    val disableHexCode: String,
    @JsonProperty("is_enabled")
    val isEnabled: Boolean
)

data class StaticMarkerColor(
    val id: Int,
    @JsonProperty("hex_code")
    val hexCode: String
)
