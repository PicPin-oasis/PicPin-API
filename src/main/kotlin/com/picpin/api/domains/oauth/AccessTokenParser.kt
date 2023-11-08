package com.picpin.api.domains.oauth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.picpin.api.verticals.domain.BusinessErrorCode
import com.picpin.api.verticals.domain.BusinessException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class AccessTokenParser(
    @Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.secret}") private val secret: String
) {
    private val key = Algorithm.HMAC256(secret.toByteArray(StandardCharsets.UTF_8))

    fun parse(accessToken: String): DecodedJWT =
        try {
            JWT.require(key)
                .withIssuer(issuer)
                .build()
                .verify(accessToken)
        } catch (exception: Exception) {
            throw BusinessException.of(
                BusinessErrorCode.INVALID_JWT_TOKEN,
                "${exception.localizedMessage}, accessToken = $accessToken"
            )
        }
}
