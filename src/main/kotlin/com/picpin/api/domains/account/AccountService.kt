package com.picpin.api.domains.account

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {
    val logger: KLogger = KotlinLogging.logger { }

    @Transactional
    fun ifNotExistsSignUp(account: Account): Account {
        val vendorId = account.vendorId

        return if (accountRepository.existsByVendorId(vendorId)) {
            accountRepository.findByVendorId(vendorId)
        } else {
            accountRepository.save(account)
        }
    }

    @Transactional(readOnly = true)
    fun findBy(accountId: Long): Account = accountRepository.findBy(accountId)
}
