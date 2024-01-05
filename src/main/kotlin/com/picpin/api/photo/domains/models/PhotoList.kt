package com.picpin.api.photo.domains.models

import com.picpin.api.photo.domains.root.Photo

data class PhotoList(val photos: List<Photo>) {

    fun unlinkAlbum() {
        photos.forEach { it.unlinkAlbum() }
    }
}
