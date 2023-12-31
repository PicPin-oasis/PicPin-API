package com.picpin.api.account.interfaces

import com.picpin.api.account.usecases.GetMyProfileUseCase
import com.picpin.api.oauth.interfaces.models.MyProfileResponse
import com.picpin.api.verticals.interfaces.model.AccountId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountApi(
    private val getMyProfileUseCase: GetMyProfileUseCase
) : AccountApiDocs {

    @GetMapping("/my-profile")
    override fun getMyProfile(@Parameter(hidden = true) @AccountId accountId: Long): ResponseEntity<MyProfileResponse> {
        val response = getMyProfileUseCase(accountId)
        return ResponseEntity.ok(response)
    }
}

@Tag(name = "사용자 API")
interface AccountApiDocs {

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
    fun getMyProfile(@Parameter(hidden = true) @AccountId accountId: Long): ResponseEntity<MyProfileResponse>
}
