package com.picpin.api.verticals.web.model

import com.picpin.api.verticals.domain.exception.BusinessErrorCode
import com.picpin.api.verticals.domain.exception.BusinessException

class JsonAccessToken(authorizationHeaderValue: String?, requestUrl: String) {
    lateinit var payload: String

    init {
        if (authorizationHeaderValue == null) {
            throw BusinessException(
                BusinessErrorCode.INVALID_VERIFIED_JWT_TOKEN,
                "Missing Authorization Header."
            )
        }

        if (!authorizationHeaderValue.contains("Bearer")) {
            throw BusinessException.from(BusinessErrorCode.IS_NOT_BEARER_TOKEN)
        }

        val splitAuthorizationHeaderValue = authorizationHeaderValue.split(" ")
        if (splitAuthorizationHeaderValue.isEmpty() || splitAuthorizationHeaderValue.size != 2) {
            BusinessException.of(
                BusinessErrorCode.INVALID_VERIFIED_JWT_TOKEN,
                " requestUrl = $requestUrl, accessToken = $authorizationHeaderValue"
            )
        }

        payload = splitAuthorizationHeaderValue.last()
    }
}
