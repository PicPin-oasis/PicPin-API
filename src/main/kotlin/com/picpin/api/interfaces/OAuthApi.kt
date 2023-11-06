package com.picpin.api.interfaces

import com.picpin.api.usecases.KakaoSocialLoginUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuthApi(
    private val kakaoSocialLoginUseCase: KakaoSocialLoginUseCase
) {

    @GetMapping("/oauth2/code/kakao")
    fun socialLogin(@RequestParam("code") authCode: String) {
        kakaoSocialLoginUseCase.process(authCode)
    }
}
