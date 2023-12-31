package com.picpin.api.verticals.interfaces.model

import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException

class JsonAccessToken(authorizationHeaderValue: String?) {
    var payload: String

    init {
        if (authorizationHeaderValue == null) {
            throw BusinessException.of(
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
                BusinessErrorCode.JWT_TOKEN_NOT_FOUND,
                "accessToken = $authorizationHeaderValue"
            )
        }

        payload = splitAuthorizationHeaderValue.last()
    }
}
