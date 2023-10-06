package com.ivy.data.repository

import com.ivy.base.model.TransactionType
import com.ivy.data.model.AccountId
import com.ivy.data.model.CategoryId
import com.ivy.data.model.Transaction
import com.ivy.data.model.TransactionId
import java.time.LocalDateTime
import java.util.UUID

interface TransactionRepository {
    suspend fun findAll(): List<Transaction>
    suspend fun findAllLimit1(): List<Transaction>
    suspend fun findAllByTypeAndAccount(
        type: TransactionType,
        accountId: AccountId
    ): List<Transaction>

    suspend fun findAllByTypeAndAccountBetween(
        type: TransactionType,
        accountId: AccountId,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllTransfersToAccount(
        toAccountId: AccountId,
        type: TransactionType = TransactionType.TRANSFER
    ): List<Transaction>

    suspend fun findAllBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllByAccountAndBetween(
        accountId: AccountId,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllByCategoryAndBetween(
        categoryId: CategoryId,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllUnspecifiedAndBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllByCategoryAndTypeAndBetween(
        categoryId: CategoryId,
        type: TransactionType,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllUnspecifiedAndTypeAndBetween(
        type: TransactionType,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllToAccountAndBetween(
        toAccountId: AccountId,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllDueToBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllDueToBetweenByCategory(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        categoryId: CategoryId
    ): List<Transaction>

    suspend fun findAllDueToBetweenByCategoryUnspecified(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<Transaction>

    suspend fun findAllDueToBetweenByAccount(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        accountId: AccountId
    ): List<Transaction>

    suspend fun findAllByRecurringRuleId(recurringRuleId: UUID): List<Transaction>
    suspend fun findById(id: TransactionId): Transaction?
    suspend fun countHappenedTransactions(): Long
    suspend fun findAllByTitleMatchingPattern(pattern: String): List<Transaction>
    suspend fun countByTitleMatchingPattern(pattern: String): Long
    suspend fun findAllByCategory(categoryId: CategoryId): List<Transaction>
    suspend fun countByTitleMatchingPatternAndCategoryId(
        pattern: String,
        categoryId: CategoryId
    ): Long

    suspend fun findAllByAccount(accountId: AccountId): List<Transaction>
    suspend fun countByTitleMatchingPatternAndAccountId(pattern: String, accountId: AccountId): Long
    suspend fun findLoanTransaction(loanId: UUID): Transaction?
    suspend fun findLoanRecordTransaction(loanRecordId: UUID): Transaction?
    suspend fun findAllByLoanId(loanId: UUID): List<Transaction>

    suspend fun save(value: Transaction)
    suspend fun saveMany(value: List<Transaction>)
    suspend fun flagDeleted(id: TransactionId)
    suspend fun flagDeletedByRecurringRuleIdAndNoDateTime(recurringRuleId: UUID)
    suspend fun flagDeletedByAccountId(accountId: AccountId)
    suspend fun deleteById(id: TransactionId)
    suspend fun deleteAllByAccountId(accountId: AccountId)
    suspend fun deleteAll()
}