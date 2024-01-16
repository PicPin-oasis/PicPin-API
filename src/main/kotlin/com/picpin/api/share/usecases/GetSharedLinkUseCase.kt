package com.picpin.api.share.usecases

import com.picpin.api.album.domains.AlbumService
import com.picpin.api.share.domains.ShareToken
import com.picpin.api.share.domains.ShareTokenGenerator
import com.picpin.api.share.domains.ShareTokenService
import com.picpin.api.share.interfaces.GetSharedLinkResponse
import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class GetSharedLinkUseCase(
    private val albumService: AlbumService,
    private val shareTokenService: ShareTokenService
) {

    operator fun invoke(accountId: Long, albumId: Long): GetSharedLinkResponse.SharedLink {
        val targetAlbum = albumService.findOneOrThrow(albumId)
        if (targetAlbum.isOwner(accountId).not()) {
            throw BusinessException.from(BusinessErrorCode.THIS_ACCOUNT_IS_NOT_OWNER)
        }

        if (shareTokenService.isExistsByOwnerIdAndAlbumId(accountId, albumId)) {
            throw BusinessException.from(BusinessErrorCode.ALREADY_EXISTS_SHARE_TOKEN)
        }

        val generatedToken = ShareTokenGenerator.generate(accountId, albumId)
        shareTokenService.save(generatedToken)

        val sharedLink = getSharedLink(generatedToken)
        return GetSharedLinkResponse.SharedLink(sharedLink)
    }

    private fun getSharedLink(generatedToken: ShareToken): String = "/share/album?token=${generatedToken.payload}"
}
