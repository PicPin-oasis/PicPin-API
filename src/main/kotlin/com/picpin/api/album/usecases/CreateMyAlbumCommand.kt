package com.picpin.api.album.usecases

import com.picpin.api.album.domains.Album

data class CreateMyAlbumCommand(
    val accountId: Long,
    val title: String,
    val coverImageUrl: String
) {

    fun toAlbum(): Album = Album(
        title,
        coverImageUrl,
        accountId
    )
}
