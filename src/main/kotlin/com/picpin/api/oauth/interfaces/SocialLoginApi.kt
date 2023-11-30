package com.picpin.api.oauth.interfaces

import com.picpin.api.account.interfaces.SocialLoginResponse
import com.picpin.api.oauth.usecases.KakaoSocialLoginUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SocialLoginApi(
    private val kakaoSocialLoginUseCase: KakaoSocialLoginUseCase
) : SocialLoginApiDocs {

    @GetMapping("/oauth2/code/kakao")
    override fun socialLogin(@RequestParam("code") authCode: String): ResponseEntity<SocialLoginResponse> {
        val response = kakaoSocialLoginUseCase.process(authCode)
        return ResponseEntity.ok(response)
    }
}

@Tag(name = "소셜 로그인 API")
interface SocialLoginApiDocs {

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
    fun socialLogin(@RequestParam("code") authCode: String): ResponseEntity<SocialLoginResponse>
}
