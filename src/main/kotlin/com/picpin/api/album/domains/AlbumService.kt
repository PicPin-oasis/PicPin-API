package com.picpin.api.album.domains

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumService(
    private val albumRepository: AlbumRepository
) {

    @Transactional(readOnly = true)
    fun findOneOrThrow(albumId: Long): Album = albumRepository.findOneOrThrow(albumId)

    @Transactional(readOnly = true)
    fun findAllByOwnerId(accountId: Long, pageable: Pageable): List<Album> =
        albumRepository.findAllByOwnerId(accountId, pageable)
            .run {
                // Test data
                val elements = this.toTypedArray()
                val startIndex = elements.lastOrNull()?.id ?: 1
                listOf(
                    *elements,
                    Album.fixture(id = startIndex),
                    Album.fixture(id = startIndex + 1, title = "부드러운 파도", coverImageUrl = "https://media.istockphoto.com/id/1450094329/ko/%EC%82%AC%EC%A7%84/%EB%B6%80%EB%93%9C%EB%9F%AC%EC%9A%B4-%ED%8C%8C%EB%8F%84%EC%99%80-%ED%95%A8%EA%BB%98-%EB%B6%84%ED%99%8D%EC%83%89-%EB%B0%94%EB%8B%A4-%ED%95%B4%EB%B3%80%EC%97%90%EC%84%9C-%EB%B3%B8-%ED%99%94%EB%A0%A4%ED%95%9C-%EC%9D%BC%EB%AA%B0.jpg?s=2048x2048&w=is&k=20&c=3YoNEnnUZF7jR9Q-LTR_pkwYw2xV27XY2KwwGAPBHq0="),
                    Album.fixture(id = startIndex + 2, title = "아름다운 열대섬", coverImageUrl = "https://cdn.pixabay.com/photo/2014/08/15/11/29/beach-418742_1280.jpg")
                )
            }

    fun save(album: Album): Album = albumRepository.save(album)
}
