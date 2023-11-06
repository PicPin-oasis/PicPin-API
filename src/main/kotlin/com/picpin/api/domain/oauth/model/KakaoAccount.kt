package com.picpin.api.domain.oauth.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.picpin.api.domain.account.AccountEntity

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoAccount(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("kakao_account")
    val kakaoAccount: Account,
    @JsonProperty("has_email")
    val hasEmail: Boolean? = null,
    @JsonProperty("is_email_valid")
    val isEmailValid: Boolean? = null,
    @JsonProperty("email")
    val email: String? = null
)

fun KakaoAccount.toAccount(): AccountEntity =
    AccountEntity(
        vendorId = id.toLong(),
        username = kakaoAccount.profile.nickname,
        profileImageUrl = kakaoAccount.profile.profileImageUrl,
        email = email
    )

data class Account(
    val profile: KakaoProfile
)

data class KakaoProfile(
    val nickname: String,
    @JsonProperty("profile_image_url")
    val profileImageUrl: String
)
