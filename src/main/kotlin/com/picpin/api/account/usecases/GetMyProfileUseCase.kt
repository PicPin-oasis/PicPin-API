package com.picpin.api.account.usecases

import com.picpin.api.account.domains.AccountService
import com.picpin.api.oauth.interfaces.models.MyProfileResponse
import com.picpin.api.verticals.stereotype.UseCase

@UseCase
class GetMyProfileUseCase(
    private val accountService: AccountService
) {

    operator fun invoke(accountId: Long): MyProfileResponse {
        val account = accountService.findOneOrThrow(accountId)
        return MyProfileResponse(account.id, account.profileImageUrl, account.username)
    }
}
