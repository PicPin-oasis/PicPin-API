package com.picpin.api.share.domains

import com.github.f4b6a3.ulid.UlidCreator
import java.time.LocalDateTime

object ShareTokenGenerator {

    fun generate(ownerId: Long, albumId: Long): ShareToken {
        val payload = UlidCreator.getUlid().toString()
        return ShareToken(payload, ownerId, albumId, LocalDateTime.now().plusWeeks(1))
    }
}
