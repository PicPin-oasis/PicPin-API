package com.picpin.api.verticals.domains.exception

import org.springframework.http.HttpStatus

enum class BusinessErrorCode(
    val errorCode: Int,
    val defaultMessage: String,
    val httpStatus: HttpStatus
) {

    // Account
    ACCOUNT_NOT_FOUND(100, "Not found account id.", HttpStatus.BAD_REQUEST),
    THIS_ACCOUNT_IS_NOT_OWNER(101, "is Not Owner.", HttpStatus.BAD_REQUEST),

    // Photo
    PHOTO_NOT_FOUND(200, "Not found photo id.", HttpStatus.BAD_REQUEST),
    CONTAINS_UNSAVED_PHOTO_IDS(201, "It contains an unsaved photo id", HttpStatus.BAD_REQUEST),
    ALREADY_STORED_IN_ALBUM_PHOTO(202, "You have a photo is already saved to an album.", HttpStatus.BAD_REQUEST),

    // Album
    ALBUM_NOT_FOUND(400, "Not found album id.", HttpStatus.BAD_REQUEST),

    // Kakao
    GET_ACCESS_TOKEN_FAILED(900, "Failed to retrieve access token.", HttpStatus.UNAUTHORIZED),
    GET_KAKAO_USER_INFO_FAILED(901, "Failed to retrieve User Info.", HttpStatus.UNAUTHORIZED),
    EXCEED_KAKAO_LOCAL_API_LIMIT(902, "Exceed API Limits", HttpStatus.BAD_REQUEST),
    GET_COORDINATE_ADDRESS_FAILED(903, "", HttpStatus.INTERNAL_SERVER_ERROR),

    // TODO
    IS_NOT_ALLOWED_REDIRECT_URL(902, "", HttpStatus.BAD_REQUEST),

    // JWT
    INVALID_VERIFIED_JWT_TOKEN(
        1000,
        "The Token's Signature resulted invalid when verified using the Algorithm",
        HttpStatus.UNAUTHORIZED
    ),
    EXPIRED_JWT_TOKEN(1001, "if the token has expired.", HttpStatus.UNAUTHORIZED),
    IS_NOT_BEARER_TOKEN(1002, "This Token is Not Bearer Type.", HttpStatus.BAD_REQUEST),
    JWT_CREATE_FAILED(1003, "", HttpStatus.INTERNAL_SERVER_ERROR),
    JWT_TOKEN_NOT_FOUND(
        1004,
        "The Token's Signature resulted invalid when verified using the Algorithm",
        HttpStatus.UNAUTHORIZED
    ),
    MISSING_CLAIN(1005, "if a claim to be verified is missing.", HttpStatus.UNAUTHORIZED),
    INCORRECT_CLAIM(1006, "if a claim contained a different value than the expected one.", HttpStatus.UNAUTHORIZED),

    // TODO
    SIGNATURE_VERIFICATION(1007, "if the signature is invalid.", HttpStatus.UNAUTHORIZED)
}
