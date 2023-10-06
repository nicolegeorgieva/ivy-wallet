package com.ivy.data.repository.mapper

import arrow.core.Either
import arrow.core.raise.either
import com.ivy.base.model.TransactionType
import com.ivy.base.time.convertToLocal
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
import java.time.ZoneOffset
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    fun TransactionEntity.toDomain(): Either<String, Transaction> = either {
        when (type) {
            TransactionType.INCOME -> Income(
                id = TransactionId(id),
                title = title.optionalString(),
                description = description.optionalString(),
                category = categoryId?.let { CategoryId(it) },
                time = parseTime().bind(),
                settled = dateTime != null,
                metadata = TransactionMetadata(recurringRuleId, loanId, loanRecordId),
                lastUpdated = Instant.EPOCH,
                removed = isDeleted,
                value = Value(
                    amount = PositiveDouble(amount),
                    asset = AssetCode("") // TODO
                )
            )

            TransactionType.EXPENSE -> Expense(
                id = TransactionId(id),
                title = title.optionalString(),
                description = description.optionalString(),
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
        return when (this) {
            is Expense -> TransactionEntity(
                accountId = id.value,
                type = TransactionType.EXPENSE,
                amount = value.amount.value,
                toAccountId = null,
                toAmount = null,
                title = null,
                description = description?.value,
                dateTime = time.convertToLocal().toLocalDateTime(),
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

            is Income -> TransactionEntity(
                accountId = id.value,
                type = TransactionType.INCOME,
                amount = value.amount.value,
                toAccountId = null,
                toAmount = null,
                title = null,
                description = description?.value,
                dateTime = time.convertToLocal().toLocalDateTime(),
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

            is Transfer -> TransactionEntity(
                accountId = id.value,
                type = TransactionType.TRANSFER,
                amount = fromValue.amount.value,
                toAccountId = null,
                toAmount = null,
                title = null,
                description = description?.value,
                dateTime = time.convertToLocal().toLocalDateTime(),
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

    private fun TransactionEntity.parseTime(): Either<String, Instant> {
        val date = dateTime ?: dueDate ?: return Either.Left("Missing transaction date")
        return Either.Right(date.toInstant(ZoneOffset.UTC))
    }

    private fun String?.optionalString(): NotBlankTrimmedString? {
        return this?.let { NotBlankTrimmedString.from(it).getOrNull() }
    }
}