package com.picpin.api.usecases

import com.picpin.api.domains.album.Album
import com.picpin.api.domains.album.AlbumService
import com.picpin.api.domains.photo.Photo
import com.picpin.api.domains.photo.PhotoService
import com.picpin.api.domains.post.Post
import com.picpin.api.domains.post.PostService
import com.picpin.api.interfaces.model.GetMyPostsByAlbumsResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetMyPostsByAlbumsUseCase(
    private val postService: PostService,
    private val photoService: PhotoService,
    private val albumService: AlbumService
) {

    fun process(accountId: Long, pageable: Pageable): GetMyPostsByAlbumsResponse.AlbumSections {
        val targetAlbums = albumService.findAllByOwnerId(accountId, pageable)
        val albumIds = targetAlbums.map { it.id }

        val targetPost = postService.findAllByAlbumIds(albumIds)
        val postIds = targetPost.map { it.id }

        val targetPhotos = photoService.findAllBy(postIds)
        val groupingPhotoByPostId = targetPhotos.groupBy { it.postId }
        val groupingPostByAlbumId = targetPost.groupBy { it.albumId!! }
        val groupingAlbumById = targetAlbums.associateBy { it.id }

        val albumSections = createAlbumSections(
            groupingPostByAlbumId = groupingPostByAlbumId,
            groupingAlbumById = groupingAlbumById,
            groupingPhotoByPostId = groupingPhotoByPostId
        )
        return GetMyPostsByAlbumsResponse.AlbumSections(albumSections)
    }

    private fun createAlbumSections(
        groupingPostByAlbumId: Map<Long, List<Post>>,
        groupingAlbumById: Map<Long, Album>,
        groupingPhotoByPostId: Map<Long, List<Photo>>
    ): List<GetMyPostsByAlbumsResponse.AlbumSection> =
        groupingPostByAlbumId.entries.map { (albumId, posts) ->
            val album = groupingAlbumById[albumId]!!
            createAlbumSection(album, posts, groupingPhotoByPostId)
        }.filter { it.postCards.isNotEmpty() }

    private fun createAlbumSection(
        album: Album,
        posts: List<Post>,
        groupingPhotoByPostId: Map<Long, List<Photo>>
    ): GetMyPostsByAlbumsResponse.AlbumSection {
        val sortedPostCards = posts.map {
            GetMyPostsByAlbumsResponse.PostCard.create(it, groupingPhotoByPostId)
        }.sortedBy { it.title }
        return GetMyPostsByAlbumsResponse.AlbumSection(album.title, sortedPostCards)
    }
}
