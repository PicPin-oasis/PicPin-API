package com.picpin.api.oauth.domains

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface KakaoRefreshTokenRepository : JpaRepository<KakaoRefreshToken, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun existsByVendorId(vendorId: Long): Boolean

    fun findByVendorId(vendorId: Long): KakaoRefreshToken
}
