package com.picpin.api.domain.oauth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.picpin.api.domain.oauth.model.CustomAccessToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class TokenGenerator(
    @Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access_token_expire_time}") private val accessTokenExpireTime: Long
) {
    private val key = Algorithm.HMAC256(secret.toByteArray(StandardCharsets.UTF_8))

    fun generateAccessToken(accountId: Long): CustomAccessToken {
        val accessExpireTime = LocalDateTime.now()
            .plusSeconds(accessTokenExpireTime)
            .atZone(ZoneId.systemDefault()).toInstant()

        val payload = JWT.create()
            .withIssuer(issuer)
            .withJWTId(accountId.toString())
            .withClaim(SNS_TYPE_NAME, KAKAO_SNS_TYPE_VALUE)
            .withExpiresAt(Date.from(accessExpireTime))
            .sign(key)

        return CustomAccessToken(payload, accessTokenExpireTime)
    }

    companion object {
        const val SNS_TYPE_NAME = "type"
        const val KAKAO_SNS_TYPE_VALUE = "KAKAO"
    }
}
