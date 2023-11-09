package com.picpin.api.usecases

import com.picpin.api.domains.account.AccountService
import com.picpin.api.domains.oauth.AccessTokenGenerator
import com.picpin.api.domains.oauth.KakaoAccessTokenReader
import com.picpin.api.domains.oauth.KakaoProfileReader
import com.picpin.api.domains.oauth.KakaoRefreshTokenService
import com.picpin.api.domains.oauth.model.JsonWebToken
import com.picpin.api.domains.oauth.model.toAccount
import com.picpin.api.interfaces.model.SocialLoginResponse
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
        val accessToken = tokenGenerator.generateAccessToken(account.id!!)
        val kakaoRefreshToken = kakaoRefreshTokenService.ifNotExistsSave(
            accountId = account.id!!,
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
