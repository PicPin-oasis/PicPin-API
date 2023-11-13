package com.picpin.api.domains.oauth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.IncorrectClaimException
import com.auth0.jwt.exceptions.MissingClaimException
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import com.picpin.api.verticals.domain.exception.BusinessErrorCode
import com.picpin.api.verticals.domain.exception.BusinessException
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
            when (exception) {
                is SignatureVerificationException -> {
                    throw BusinessException.of(
                        BusinessErrorCode.EXPIRED_JWT_TOKEN,
                        "${exception.localizedMessage}, accessToken = $accessToken"
                    )
                }
                is TokenExpiredException -> {
                    throw BusinessException.of(
                        BusinessErrorCode.EXPIRED_JWT_TOKEN,
                        "${exception.localizedMessage}, accessToken = $accessToken"
                    )
                }
                is IncorrectClaimException -> {
                    throw BusinessException.of(
                        BusinessErrorCode.INCORRECT_CLAIM,
                        "${exception.localizedMessage}, accessToken = $accessToken"
                    )
                }
                is MissingClaimException -> {
                    throw BusinessException.of(
                        BusinessErrorCode.MISSING_CLAIN,
                        "${exception.localizedMessage}, accessToken = $accessToken"
                    )
                }
                else -> {
                    throw BusinessException.of(
                        BusinessErrorCode.INVALID_VERIFIED_JWT_TOKEN,
                        "${exception.localizedMessage}, accessToken = $accessToken"
                    )
                }
            }
        }
}
