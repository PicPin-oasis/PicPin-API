package com.picpin.api.usecases

import com.picpin.api.domain.account.AccountService
import com.picpin.api.domain.oauth.KakaoAccessTokenReader
import com.picpin.api.domain.oauth.KakaoProfileReader
import com.picpin.api.domain.oauth.model.toAccount
import com.picpin.api.interfaces.model.OAuthResponse
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class KakaoSocialLoginUseCase(
    private val kakaoAccessTokenReader: KakaoAccessTokenReader,
    private val kakaoProfileReader: KakaoProfileReader,
    private val accountService: AccountService
) {
    val logger: KLogger = KotlinLogging.logger { }

    fun process(authCode: String): OAuthResponse {
        val accessToken = kakaoAccessTokenReader.getAccessToken(authCode)
        val account = kakaoProfileReader.getKakaoAccount(accessToken.tokenType, accessToken.payload)
        accountService.ifNotExistsSignUp(account.toAccount())

        return OAuthResponse(accessToken.payload, accessToken.refreshToken, accessToken.tokenType)
    }
}
