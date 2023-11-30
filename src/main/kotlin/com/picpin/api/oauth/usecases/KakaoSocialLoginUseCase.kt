package com.picpin.api.oauth.usecases

import com.picpin.api.account.domains.AccountService
import com.picpin.api.account.interfaces.SocialLoginResponse
import com.picpin.api.oauth.domains.KakaoAccessTokenReader
import com.picpin.api.oauth.domains.KakaoProfileReader
import com.picpin.api.oauth.domains.access.AccessTokenGenerator
import com.picpin.api.oauth.domains.models.JsonWebToken
import com.picpin.api.oauth.domains.models.toAccount
import com.picpin.api.oauth.domains.refresh.KakaoRefreshTokenService
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class KakaoSocialLoginUseCase(
    private val kakaoAccessTokenReader: KakaoAccessTokenReader,
    private val kakaoProfileReader: KakaoProfileReader,
    private val accountService: AccountService,
    private val tokenGenerator: AccessTokenGenerator,
    private val kakaoRefreshTokenService: KakaoRefreshTokenService
) {
    val logger: KLogger = KotlinLogging.logger { }

    fun process(authCode: String): SocialLoginResponse {
        val kakaoAccessToken = kakaoAccessTokenReader.getAccessToken(authCode)
        val kakaoUserInfo = kakaoProfileReader.getKakaoUserInfo(
            tokenType = kakaoAccessToken.tokenType,
            accessToken = kakaoAccessToken.payload
        )

        val account = accountService.ifNotExistsSignUp(kakaoUserInfo.toAccount())
        val accessToken = tokenGenerator.generateAccessToken(account.id)
        val kakaoRefreshToken = kakaoRefreshTokenService.ifNotExistsSave(
            accountId = account.id,
            vendorId = account.vendorId,
            payload = kakaoAccessToken.refreshToken,
            expireDateTimeToSec = kakaoAccessToken.refreshTokenExpiresIn.toLong()
        )

        return SocialLoginResponse(
            jsonWebToken = accessToken,
            refreshToken = JsonWebToken.from(kakaoRefreshToken),
            tokenType = kakaoAccessToken.tokenType
        )
    }
}
