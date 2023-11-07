package com.picpin.api.usecases

import com.picpin.api.domain.account.AccountService
import com.picpin.api.domain.oauth.KakaoAccessTokenReader
import com.picpin.api.domain.oauth.KakaoProfileReader
import com.picpin.api.domain.oauth.TokenGenerator
import com.picpin.api.domain.oauth.model.toAccount
import com.picpin.api.interfaces.model.OAuthResponse
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class KakaoSocialLoginUseCase(
    private val kakaoAccessTokenReader: KakaoAccessTokenReader,
    private val kakaoProfileReader: KakaoProfileReader,
    private val accountService: AccountService,
    private val tokenGenerator: TokenGenerator
) {
    val logger: KLogger = KotlinLogging.logger { }

    fun process(authCode: String): OAuthResponse {
        val kakaoAccessToken = kakaoAccessTokenReader.getAccessToken(authCode)
        val kakaoUserInfo = kakaoProfileReader.getKakaoUserInfo(
            tokenType = kakaoAccessToken.tokenType,
            accessToken = kakaoAccessToken.payload
        )

        val account = accountService.ifNotExistsSignUp(kakaoUserInfo.toAccount())
        val accessToken = tokenGenerator.generateAccessToken(account.id!!)

        return OAuthResponse(accessToken, kakaoAccessToken.tokenType)
    }
}
