package com.picpin.api.account.usecases

import com.picpin.api.account.domains.AccountService
import com.picpin.api.oauth.interfaces.MyProfileResponse
import org.springframework.stereotype.Service

@Service
class GetMyProfileUseCase(
    private val accountService: AccountService
) {

    fun process(accountId: Long): MyProfileResponse {
        val account = accountService.findOneOrThrow(accountId)
        return MyProfileResponse(account.id, account.profileImageUrl, account.username)
    }
}
