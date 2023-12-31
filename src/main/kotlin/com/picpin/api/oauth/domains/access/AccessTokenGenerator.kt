package com.picpin.api.oauth.domains.access

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.picpin.api.oauth.domains.models.JsonWebToken
import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

@Component
class AccessTokenGenerator(
    @Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access_token_expire_time}") private val accessTokenExpireTime: Long
) {
    private val key = Algorithm.HMAC256(secret.toByteArray(StandardCharsets.UTF_8))

    fun generateAccessToken(accountId: Long): JsonWebToken {
        val accessExpireTime = getAccessTokenExpireTime()
        val accessToken = createAccessToken(accountId, accessExpireTime)
        return JsonWebToken(accessToken, accessExpireTime.epochSecond)
    }

    private fun getAccessTokenExpireTime(): Instant {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"))
            .plusSeconds(accessTokenExpireTime)
            .toInstant(ZoneOffset.of("+09:00"))
    }

    private fun createAccessToken(
        accountId: Long,
        accessExpireTime: Instant
    ) = try {
        JWT.create()
            .withIssuer(issuer)
            .withJWTId(accountId.toString())
            .withClaim(SNS_TYPE_NAME, KAKAO_SNS_TYPE_VALUE)
            .withExpiresAt(Date.from(accessExpireTime))
            .sign(key)
    } catch (exception: Exception) {
        throw BusinessException.of(
            BusinessErrorCode.JWT_CREATE_FAILED,
            "${exception.localizedMessage}, accountId = $accountId"
        )
    }

    companion object {
        const val SNS_TYPE_NAME = "type"
        const val KAKAO_SNS_TYPE_VALUE = "KAKAO"
    }
}
