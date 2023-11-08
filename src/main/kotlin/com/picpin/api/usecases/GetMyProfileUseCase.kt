package com.picpin.api.usecases

import com.picpin.api.domain.account.AccountService
import com.picpin.api.interfaces.model.MyProfileResponse
import org.springframework.stereotype.Service

@Service
class GetMyProfileUseCase(
    private val accountService: AccountService
) {

    fun process(accountId: Long): MyProfileResponse {
        val account = accountService.findBy(accountId)
        return MyProfileResponse(account.id!!, account.profileImageUrl, account.username)
    }
}
