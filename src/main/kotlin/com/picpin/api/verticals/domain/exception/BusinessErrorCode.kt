package com.picpin.api.verticals.domain.exception

import org.springframework.http.HttpStatus

enum class BusinessErrorCode(
    val errorCode: Int,
    val defaultMessage: String,
    val httpStatus: HttpStatus
) {

    // Account
    ACCOUNT_NOT_FOUND(100, "Not found account id.", HttpStatus.NOT_FOUND),
    THIS_ACCOUNT_IS_NOT_OWNER(101, "is Not Owner.", HttpStatus.NOT_FOUND),

    // Post
    POST_NOT_FOUND(200, "Not found post id.", HttpStatus.NOT_FOUND),
    CONTAINS_UNSAVED_POST_IDS(201, "It contains an unsaved post id", HttpStatus.NOT_FOUND),
    ALREADY_STORED_IN_ALBUM_POST(201, "You have a post is already saved to an album.", HttpStatus.NOT_FOUND),

    // Post coordinate
    POST_COORDINATE_NOT_FOUND(200, "Not found post id.", HttpStatus.NOT_FOUND),

    // Album
    ALBUM_NOT_FOUND(400, "Not found album id.", HttpStatus.NOT_FOUND),

    // Marker
    MARKER_COLOR_NOT_FOUND(800, "Not found marker color id.", HttpStatus.NOT_FOUND),

    // Kakao
    GET_ACCESS_TOKEN_FAILED(900, "Failed to retrieve access token.", HttpStatus.UNAUTHORIZED),
    GET_KAKAO_USER_INFO_FAILED(901, "Failed to retrieve User Info.", HttpStatus.UNAUTHORIZED),

    // JWT
    INVALID_VERIFIED_JWT_TOKEN(
        1000,
        "The Token's Signature resulted invalid when verified using the Algorithm",
        HttpStatus.UNAUTHORIZED
    ),
    EXPIRED_JWT_TOKEN(1001, "This Token is Expired.", HttpStatus.UNAUTHORIZED),
    IS_NOT_BEARER_TOKEN(1002, "This Token is Not Bearer Type.", HttpStatus.BAD_REQUEST),
    JWT_CREATE_FAILED(1003, "", HttpStatus.INTERNAL_SERVER_ERROR)
}
