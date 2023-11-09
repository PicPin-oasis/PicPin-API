package com.picpin.api.domains.oauth

import com.picpin.api.domains.oauth.model.KakaoUserInfo
import com.picpin.api.verticals.domain.exception.BusinessErrorCode
import com.picpin.api.verticals.domain.exception.BusinessException
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
                throw BusinessException.from(BusinessErrorCode.GET_KAKAO_USER_INFO_FAILED)
            }
        } catch (exception: Exception) {
            throw BusinessException.of(
                BusinessErrorCode.GET_KAKAO_USER_INFO_FAILED,
                " ${exception.localizedMessage}"
            )
        }
    }

    private fun getHttpHeaders(tokenType: String, accessToken: String): HttpHeaders {
        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "$tokenType $accessToken")
        return headers
    }
}
