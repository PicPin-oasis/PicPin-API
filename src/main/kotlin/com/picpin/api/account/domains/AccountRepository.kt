package com.picpin.api.account.domains

import com.picpin.api.verticals.domains.exception.BusinessErrorCode
import com.picpin.api.verticals.domains.exception.BusinessException
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface AccountRepository : JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun existsByVendorId(vendorId: Long): Boolean

    fun findByVendorId(vendorId: Long): Account
}

fun AccountRepository.findOneOrThrow(accountId: Long): Account {
    return findById(accountId).orElseThrow {
        BusinessException.of(BusinessErrorCode.ACCOUNT_NOT_FOUND, "accountId = $accountId")
    }
}
