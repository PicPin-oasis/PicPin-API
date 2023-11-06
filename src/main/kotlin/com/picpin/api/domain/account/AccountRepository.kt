package com.picpin.api.domain.account

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface AccountRepository : JpaRepository<AccountEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun existsByVendorId(vendorId: Long): Boolean

    fun findByVendorId(vendorId: Long): AccountEntity
}

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {
    val logger: KLogger = KotlinLogging.logger { }

    @Transactional
    fun ifNotExistsSignUp(accountEntity: AccountEntity) {
        val vendorId = accountEntity.vendorId

        if (accountRepository.existsByVendorId(vendorId)) {
            accountRepository.findByVendorId(vendorId)
        } else {
            accountRepository.save(accountEntity)
        }
    }
}
