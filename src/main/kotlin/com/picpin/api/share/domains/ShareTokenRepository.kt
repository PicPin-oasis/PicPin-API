package com.picpin.api.share.domains

import org.springframework.data.jpa.repository.JpaRepository

interface ShareTokenRepository : JpaRepository<ShareToken, Long> {
    fun existsByOwnerIdAndAlbumId(ownerId: Long, albumId: Long): Boolean
    fun findByPayload(payload: String): ShareToken
}
