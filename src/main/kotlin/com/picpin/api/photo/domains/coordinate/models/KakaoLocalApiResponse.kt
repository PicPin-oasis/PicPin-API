package com.picpin.api.photo.domains.coordinate.models

data class KakaoLocalApiResponse(
    val meta: KakaoMeta,
    val documents: List<KakaoDocument>
) {

    fun lookup(address: String): KakaoDocument =
        if (documents.isNotEmpty()) {
            documents.first()
        } else {
            KakaoDocument.empty(address)
        }
}
