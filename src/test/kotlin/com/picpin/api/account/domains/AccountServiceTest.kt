package com.picpin.api.account.domains

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AccountServiceTest : DescribeSpec({

    describe("ifNotExistsSignUp는") {
        context("기존에 회원 가입된 사용자라면") {
            val accountRepository = mockk<AccountRepository>()
            val accountService = AccountService(accountRepository)
            val fixture = Account.fixture()

            every { accountRepository.existsByVendorId(fixture.vendorId) } returns true
            every { accountRepository.findByVendorId(fixture.vendorId) } returns fixture

            it("vendor id로 사용자를 조회한다.") {
                val account = accountService.ifNotExistsSignUp(fixture)

                fixture shouldBeEqualToComparingFields account
                verify { accountRepository.findByVendorId(fixture.vendorId) }
            }
        }

        context("기존에 회원 가입하지 않은 사용자라면") {
            val accountRepository = mockk<AccountRepository>()
            val accountService = AccountService(accountRepository)
            val fixture = Account.fixture()

            every { accountRepository.existsByVendorId(fixture.vendorId) } returns false
            every { accountRepository.save(fixture) } returns fixture

            it("사용자를 새로 저장한다.") {
                val account = accountService.ifNotExistsSignUp(fixture)

                fixture shouldBeEqualToComparingFields account
                verify { accountRepository.save(fixture) }
            }
        }
    }
})
