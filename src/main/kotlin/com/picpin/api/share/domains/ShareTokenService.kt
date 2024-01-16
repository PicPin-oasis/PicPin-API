package com.picpin.api.share.domains

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ShareTokenService(
    private val shareTokenRepository: ShareTokenRepository
) {

    fun isExistsByOwnerIdAndAlbumId(accountId: Long, albumId: Long): Boolean =
        shareTokenRepository.existsByOwnerIdAndAlbumId(accountId, albumId)

    @Transactional(readOnly = true)
    fun findByPayload(payload: String): ShareToken = shareTokenRepository.findByPayload(payload)

    fun save(shareToken: ShareToken): ShareToken = shareTokenRepository.save(shareToken)
}
