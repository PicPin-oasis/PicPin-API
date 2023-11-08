package com.picpin.api.domains.oauth.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domains.account.Account

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUserInfo(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount,
    @JsonProperty("has_email")
    val hasEmail: Boolean? = null,
    @JsonProperty("is_email_valid")
    val isEmailValid: Boolean? = null,
    @JsonProperty("email")
    val email: String? = null
)

fun KakaoUserInfo.toAccount(): Account =
    Account(
        vendorId = id.toLong(),
        username = kakaoAccount.profile.nickname,
        profileImageUrl = kakaoAccount.profile.profileImageUrl,
        email = email
    )

data class KakaoAccount(
    val profile: KakaoProfile
)

data class KakaoProfile(
    val nickname: String,
    @JsonProperty("profile_image_url")
    val profileImageUrl: String
)
