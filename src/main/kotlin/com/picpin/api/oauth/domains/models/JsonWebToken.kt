package com.picpin.api.oauth.domains.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.oauth.domains.refresh.KakaoRefreshToken
import java.time.ZoneOffset

data class JsonWebToken(
    val payload: String,
    @JsonProperty("expire_time")
    val expireTime: Long
) {

    companion object {
        fun from(kakaoRefreshToken: KakaoRefreshToken): JsonWebToken =
            JsonWebToken(
                payload = kakaoRefreshToken.payload,
                expireTime = kakaoRefreshToken.expireDateTime.toEpochSecond(ZoneOffset.of("+09:00"))
            )
    }
}
