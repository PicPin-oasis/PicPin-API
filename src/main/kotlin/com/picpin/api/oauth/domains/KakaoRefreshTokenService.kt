package com.picpin.api.oauth.domains

import com.picpin.api.verticals.domains.base.DEFAULT_ZONE_ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class KakaoRefreshTokenService(
    private val kakaoRefreshTokenRepository: KakaoRefreshTokenRepository
) {

    @Transactional
    fun ifNotExistsSave(
        accountId: Long,
        vendorId: Long,
        payload: String,
        expireDateTimeToSec: Long
    ): KakaoRefreshToken = if (kakaoRefreshTokenRepository.existsByVendorId(vendorId)) {
        kakaoRefreshTokenRepository.findByVendorId(vendorId)
    } else {
        val kakaoRefreshToken = KakaoRefreshToken(
            vendorId = vendorId,
            accountId = accountId,
            payload = payload,
            expireDateTime = LocalDateTime.now(DEFAULT_ZONE_ID).plusSeconds(expireDateTimeToSec)
        )
        kakaoRefreshTokenRepository.save(kakaoRefreshToken)
    }
}
