package com.ivy.data.repository.mapper

import arrow.core.Either
import com.ivy.data.db.entity.TransactionEntity
import com.ivy.data.model.Transaction
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    fun TransactionEntity.toDomain(): Either<String, Transaction> {

    }

    fun Transaction.toEntity(): TransactionEntity {

    }
}