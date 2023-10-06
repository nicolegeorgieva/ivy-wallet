package com.ivy.data.repository.mapper

import arrow.core.Either
import arrow.core.raise.either
import com.ivy.base.model.TransactionType
import com.ivy.data.db.entity.TransactionEntity
import com.ivy.data.model.CategoryId
import com.ivy.data.model.Transaction
import com.ivy.data.model.TransactionId
import com.ivy.data.model.TransactionMetadata
import com.ivy.data.model.primitive.NotBlankTrimmedString
import java.time.Instant
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    fun TransactionEntity.toDomain(): Either<String, Transaction> = either {
        when (type) {
            TransactionType.INCOME ->
                TransactionType.EXPENSE

            ->
                TransactionType.TRANSFER

            ->
        }
        Transaction(
            id = TransactionId(id),
            title = title?.let { NotBlankTrimmedString.from(it) },
            description = description?.let { NotBlankTrimmedString.from(it) },
            category = CategoryId(categoryId),
            time = Instant.EPOCH,
            settled =,
            metadata = TransactionMetadata,
            lastUpdated = Instant.EPOCH,
            removed = isDeleted
        )
    }

    fun Transaction.toEntity(): TransactionEntity {
        return TransactionEntity(
            accountId =,
            type = TransactionType,
            amount = Double,
            toAccountId = null,
            toAmount = null,
            title = null,
            description = description?.value,
            dateTime = time,
            categoryId = category?.value,
            dueDate = null,
            recurringRuleId = null,
            attachmentUrl = null,
            loanId = null,
            loanRecordId = null,
            isSynced = false,
            isDeleted = removed,
            id = id.value
        )
    }
}