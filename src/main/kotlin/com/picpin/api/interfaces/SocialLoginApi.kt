package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.SocialLoginResponse
import com.picpin.api.usecases.KakaoSocialLoginUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "소셜 로그인 API")
@RestController
class SocialLoginApi(
    private val kakaoSocialLoginUseCase: KakaoSocialLoginUseCase
) {

    @Operation(
        method = "GET",
        summary = "소셜 로그인",
        parameters = [
            Parameter(
                name = "code",
                `in` = ParameterIn.QUERY,
                description = "Kakao Auth Code",
                required = true
            )
        ]
    )
    @GetMapping("/oauth2/code/kakao")
    fun socialLogin(@RequestParam("code") authCode: String): ResponseEntity<SocialLoginResponse> {
        val response = kakaoSocialLoginUseCase.process(authCode)
        return ResponseEntity.ok(response)
    }
}
