package com.picpin.api.oauth.domains

import com.picpin.api.oauth.domains.model.KakaoAccessToken
import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class KakaoAccessTokenReader(
    private val restTemplate: RestTemplate,
    @Value("\${oauth2.access_token_url}") private val accessTokenUrl: String,
    @Value("\${oauth2.redirect_url}") private val redirectUrl: String,
    @Value("\${oauth2.client_id}") private val clientId: String,
    @Value("\${oauth2.client_secret}") private val clientSecret: String? = null
) {
    val logger: KLogger = KotlinLogging.logger { }

    fun getAccessToken(authCode: String): KakaoAccessToken {
        val httpHeaders = getHttpHeaders()
        val requestParams: MultiValueMap<String, String> = getRequestParams(authCode)
        val requestEntity: HttpEntity<MultiValueMap<String, String>> =
            HttpEntity(requestParams, httpHeaders)

        try {
            val responseEntity = restTemplate.postForEntity(
                accessTokenUrl,
                requestEntity,
                KakaoAccessToken::class.java
            )

            val statusCode = responseEntity.statusCode
            if (statusCode.is2xxSuccessful) {
                return responseEntity.body!!
            } else {
                logger.warn { "getAccessToken() failed. reason status code = $statusCode" }
                throw BusinessException.from(BusinessErrorCode.GET_ACCESS_TOKEN_FAILED)
            }
        } catch (exception: Exception) {
            throw BusinessException.of(
                BusinessErrorCode.GET_ACCESS_TOKEN_FAILED,
                " ${exception.localizedMessage}"
            )
        }
    }

    private fun getHttpHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Accept", "application/json")
        return headers
    }

    private fun getRequestParams(authCode: String): MultiValueMap<String, String> {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", clientId)
        params.add("redirect_uri", redirectUrl)
        params.add("code", authCode)

        if (clientSecret != null) {
            params.add("client_secret", clientSecret)
        }

        return params
    }
}
