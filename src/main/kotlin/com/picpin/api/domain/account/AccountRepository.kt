package com.picpin.api.domain.account

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface AccountRepository : JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun existsByVendorId(vendorId: Long): Boolean

    fun findByVendorId(vendorId: Long): Account

    fun findBy(accountId: Long): Account {
        return findById(accountId).orElseThrow {
            RuntimeException("accountId = $accountId")
        }
    }
}
