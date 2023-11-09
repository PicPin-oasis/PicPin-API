package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.SocialLoginResponse
import com.picpin.api.usecases.KakaoSocialLoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SocialLoginApi(
    private val kakaoSocialLoginUseCase: KakaoSocialLoginUseCase
) {

    @GetMapping("/oauth2/code/kakao")
    fun socialLogin(@RequestParam("code") authCode: String): ResponseEntity<SocialLoginResponse> {
        val response = kakaoSocialLoginUseCase.process(authCode)
        return ResponseEntity.ok(response)
    }
}
