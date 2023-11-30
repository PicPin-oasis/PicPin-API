package com.picpin.api.photo.domains.coordinate

import com.picpin.api.photo.domains.coordinate.models.KakaoLocalApiResponse
import com.picpin.api.photo.domains.coordinate.models.KakaoLocalCoordinate
import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Service
class KakaoCoordinateProvider(
    private val restTemplate: RestTemplate,
    @Value("\${coordinate.rest_api_key}")
    private val restApiKey: String,
    @Value("\${coordinate.local_api_url}")
    private val localApiUrl: String
) {
    val logger: KLogger = KotlinLogging.logger { }

    fun getCoordinateByAddress(address: String): KakaoLocalCoordinate {
        val httpHeaders = httpHeaders()
        val requestUrl = getRequestUrl(address)
        val requestEntity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(httpHeaders)

        try {
            val responseEntity = restTemplate.exchange<KakaoLocalApiResponse>(
                url = requestUrl,
                method = HttpMethod.GET,
                requestEntity
            )

            val statusCode = responseEntity.statusCode
            if (statusCode.is2xxSuccessful) {
                val response = responseEntity.body!!
                val document = response.lookup(address)
                val provinceCode = document.address.getProvinceId()
                val coordinate = PointFactory.create(document.longitude.toString(), document.latitude.toString())

                return KakaoLocalCoordinate(provinceCode, coordinate)
            } else if (statusCode.is4xxClientError && statusCode.isSameCodeAs(HttpStatusCode.valueOf(429))) {
                logger.warn { "getCoordinateByAddress() failed. reason status code = $statusCode" }
                throw BusinessException.from(BusinessErrorCode.EXCEED_KAKAO_LOCAL_API_LIMIT)
            } else {
                logger.warn { "getCoordinateByAddress() failed. reason status code = $statusCode" }
                throw BusinessException.from(BusinessErrorCode.GET_COORDINATE_ADDRESS_FAILED)
            }
        } catch (exception: Exception) {
            throw BusinessException.of(BusinessErrorCode.GET_COORDINATE_ADDRESS_FAILED, exception.localizedMessage)
        }
    }

    private fun getRequestUrl(address: String): URI {
        val builder = UriComponentsBuilder.fromHttpUrl(localApiUrl)
        return builder.queryParam("query", address).build().encode().toUri()
    }

    private fun httpHeaders(): HttpHeaders {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_FORM_URLENCODED
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "KakaoAK $restApiKey")
        return httpHeaders
    }
}
