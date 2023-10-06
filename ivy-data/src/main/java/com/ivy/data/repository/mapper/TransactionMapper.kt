package com.ivy.data.repository.mapper

import arrow.core.Either
import arrow.core.raise.either
import com.ivy.base.model.TransactionType
import com.ivy.data.db.entity.TransactionEntity
import com.ivy.data.model.AccountId
import com.ivy.data.model.CategoryId
import com.ivy.data.model.Expense
import com.ivy.data.model.Income
import com.ivy.data.model.Transaction
import com.ivy.data.model.TransactionId
import com.ivy.data.model.TransactionMetadata
import com.ivy.data.model.Transfer
import com.ivy.data.model.common.Value
import com.ivy.data.model.primitive.AssetCode
import com.ivy.data.model.primitive.NotBlankTrimmedString
import com.ivy.data.model.primitive.PositiveDouble
import java.time.Instant
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    fun TransactionEntity.toDomain(): Either<String, Transaction> = either {
        when (type) {
            TransactionType.INCOME -> Income(
                id = TransactionId(id),
                title = title?.let { NotBlankTrimmedString(it) },
                description = description?.let { NotBlankTrimmedString(it) },
                category = categoryId?.let { CategoryId(it) },
                time = Instant.EPOCH,
                settled = false,// TODO
                metadata = TransactionMetadata(recurringRuleId, loanId, loanRecordId),
                lastUpdated = Instant.EPOCH,// TODO
                removed = isDeleted,
                value = Value(amount = PositiveDouble(amount), asset = AssetCode(""))// TODO
            )

            TransactionType.EXPENSE -> Expense(
                id = TransactionId(id),
                title = title?.let { NotBlankTrimmedString(it) },
                description = description?.let { NotBlankTrimmedString(it) },
                category = categoryId?.let { CategoryId(it) },
                time = Instant.EPOCH,
                settled = false,// TODO
                metadata = TransactionMetadata(recurringRuleId, loanId, loanRecordId),
                lastUpdated = Instant.EPOCH,// TODO
                removed = isDeleted,
                value = Value(amount = PositiveDouble(amount), asset = AssetCode(""))// TODO
            )

            TransactionType.TRANSFER -> Transfer(
                id = TransactionId(id),
                title = title?.let { NotBlankTrimmedString(it) },
                description = description?.let { NotBlankTrimmedString(it) },
                category = categoryId?.let { CategoryId(it) },
                time = Instant.EPOCH,
                settled = false,// TODO
                metadata = TransactionMetadata(recurringRuleId, loanId, loanRecordId),
                lastUpdated = Instant.EPOCH,// TODO
                removed = isDeleted,
                fromAccount = AccountId(accountId),
                fromValue = Value(amount = PositiveDouble(amount), asset = AssetCode("")),
                toAccount = AccountId(accountId),
                toValue = Value(amount = PositiveDouble(amount), asset = AssetCode(""))
            )
        }
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