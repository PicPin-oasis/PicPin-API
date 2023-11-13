package com.picpin.api.verticals.domains

class BusinessException(
    val errorCode: BusinessErrorCode,
    val reason: String
) : RuntimeException() {

    companion object {
        fun from(errorCode: BusinessErrorCode): BusinessException {
            return BusinessException(errorCode, "")
        }

        fun of(errorCode: BusinessErrorCode, cause: String): BusinessException {
            return BusinessException(errorCode, cause)
        }
    }
}
