package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.MyProfileResponse
import com.picpin.api.usecases.GetMyProfileUseCase
import com.picpin.api.verticals.web.model.AccountId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "사용자 API")
@RestController
@RequestMapping("/accounts")
class AccountApi(
    private val getMyProfileUseCase: GetMyProfileUseCase
) {

    @Operation(
        method = "GET",
        summary = "내 프로필 정보 가져오기",
        parameters = [
            Parameter(
                name = HttpHeaders.AUTHORIZATION,
                `in` = ParameterIn.HEADER,
                description = "JWT Token",
                example = "Bearer eyjhbGciOiJIUz...",
                required = true
            )
        ]
    )
    @GetMapping("/my-profile")
    fun getMyProfile(
        @Parameter(hidden = true) @AccountId accountId: Long
    ): ResponseEntity<MyProfileResponse> {
        val response = getMyProfileUseCase.process(accountId)
        return ResponseEntity.ok(response)
    }
}
