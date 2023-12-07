package com.picpin.api.share.interfaces

import com.picpin.api.share.usecases.CreateSharedLinkUseCase
import com.picpin.api.share.usecases.GetSharedAlbumDetailUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/share")
class ShareApi(
    private val createSharedLinkUseCase: CreateSharedLinkUseCase,
    private val getSharedAlbumDetail: GetSharedAlbumDetailUseCase
) {

    @GetMapping
    fun createSharedLink() {
    }

    @GetMapping("/album")
    fun getSharedAlbumDetail() {
    }
}
