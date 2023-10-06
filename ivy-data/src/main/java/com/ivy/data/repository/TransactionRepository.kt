package com.ivy.data.repository

import com.ivy.base.model.TransactionType
import com.ivy.data.model.Transaction
import java.time.LocalDateTime
import java.util.UUID

interface TransactionRepository {
    suspend fun findAll(): List<Transaction>
    suspend fun findAllLimit1(): List<Transaction>
    suspend fun findAllByTypeAndAccount(
        type: TransactionType,
        accountId: UUID
    ): List<Transaction>

    suspend fun findAllByTypeAndAccountBetween(
        type: TransactionType,
        accountId: UUID,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllTransfersToAccount(
        toAccountId: UUID,
        type: TransactionType = TransactionType.TRANSFER
    ): List<Transaction>

    suspend fun findAllBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllByAccountAndBetween(
        accountId: UUID,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllByCategoryAndBetween(
        categoryId: UUID,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllUnspecifiedAndBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction>

    suspend fun findAllByCategoryAndTypeAndBetween(
        categoryId: UUID,
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
        toAccountId: UUID,
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
        categoryId: UUID
    ): List<Transaction>

    suspend fun findAllDueToBetweenByCategoryUnspecified(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<Transaction>

    suspend fun findAllDueToBetweenByAccount(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        accountId: UUID
    ): List<Transaction>

    suspend fun findAllByRecurringRuleId(recurringRuleId: UUID): List<Transaction>
    suspend fun findById(id: UUID): Transaction?
    suspend fun countHappenedTransactions(): Long
    suspend fun findAllByTitleMatchingPattern(pattern: String): List<Transaction>
    suspend fun countByTitleMatchingPattern(pattern: String): Long
    suspend fun findAllByCategory(categoryId: UUID): List<Transaction>
    suspend fun countByTitleMatchingPatternAndCategoryId(
        pattern: String,
        categoryId: UUID
    ): Long

    suspend fun findAllByAccount(accountId: UUID): List<Transaction>
    suspend fun countByTitleMatchingPatternAndAccountId(pattern: String, accountId: UUID): Long
    suspend fun findLoanTransaction(loanId: UUID): Transaction?
    suspend fun findLoanRecordTransaction(loanRecordId: UUID): Transaction?
    suspend fun findAllByLoanId(loanId: UUID): List<Transaction>

    suspend fun save(value: Transaction)
    suspend fun saveMany(value: List<Transaction>)
    suspend fun flagDeleted(id: UUID)
    suspend fun flagDeletedByRecurringRuleIdAndNoDateTime(recurringRuleId: UUID)
    suspend fun flagDeletedByAccountId(accountId: UUID)
    suspend fun deleteById(id: UUID)
    suspend fun deleteAllByAccountId(accountId: UUID)
    suspend fun deleteAll()
}