package com.picpin.api.verticals.domains.database

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.function.Supplier

@Component
class TransactionHandler {

    @Transactional(propagation = Propagation.REQUIRED)
    fun <T> runInTransaction(supplier: Supplier<T>): T = supplier.get()
}
