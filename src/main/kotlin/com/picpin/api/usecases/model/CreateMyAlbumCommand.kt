package com.picpin.api.usecases.model

import com.picpin.api.domains.album.Album

data class CreateMyAlbumCommand(
    val accountId: Long,
    val title: String,
    val coverImageUrl: String,
    val postIds: List<Long>
) {

    fun toAlbum(): Album = Album(
        title,
        coverImageUrl,
        accountId
    )
}
