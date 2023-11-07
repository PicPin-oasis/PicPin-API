package com.picpin.api.domain.oauth

import com.picpin.api.domain.oauth.model.KakaoUserInfo
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class KakaoProfileReader(
    private val restTemplate: RestTemplate,
    @Value("\${oauth2.get_user_info_url}") private val getUserInfoUrl: String
) {
    val logger: KLogger = KotlinLogging.logger { }

    fun getKakaoUserInfo(tokenType: String, accessToken: String): KakaoUserInfo {
        val httpHeaders = getHttpHeaders(tokenType, accessToken)
        val requestEntity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(httpHeaders)

        try {
            val responseEntity = restTemplate.exchange<KakaoUserInfo>(
                url = getUserInfoUrl,
                method = HttpMethod.GET,
                requestEntity = requestEntity
            )

            val statusCode = responseEntity.statusCode
            if (statusCode.is2xxSuccessful) {
                return responseEntity.body!!
            } else {
                logger.warn { "getKakaoAccount() failed. reason status code = $statusCode" }
                throw RuntimeException("Failed to retrieve access token")
            }
        } catch (exception: Exception) {
            logger.info { "exception = $exception" }
            throw RuntimeException("Failed to retrieve access token")
        }
    }

    private fun getHttpHeaders(tokenType: String, accessToken: String): HttpHeaders {
        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "$tokenType $accessToken")
        return headers
    }
}
