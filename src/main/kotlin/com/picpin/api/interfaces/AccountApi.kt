package com.picpin.api.interfaces

import com.picpin.api.interfaces.model.MyProfileResponse
import com.picpin.api.usecases.GetMyProfileUseCase
import com.picpin.api.verticals.web.model.AccountId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountApi(
    private val getMyProfileUseCase: GetMyProfileUseCase
) {

    @GetMapping("/my-profile")
    fun getMyProfile(@AccountId accountId: Long): ResponseEntity<MyProfileResponse> {
        val response = getMyProfileUseCase.process(accountId)
        return ResponseEntity.ok(response)
    }
}
