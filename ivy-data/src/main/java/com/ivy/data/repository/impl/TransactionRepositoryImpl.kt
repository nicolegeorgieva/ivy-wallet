package com.ivy.data.repository.impl

import com.ivy.base.model.TransactionType
import com.ivy.data.model.AccountId
import com.ivy.data.model.CategoryId
import com.ivy.data.model.Transaction
import com.ivy.data.model.TransactionId
import com.ivy.data.repository.TransactionRepository
import com.ivy.data.repository.mapper.TransactionMapper
import com.ivy.data.source.LocalTransactionDataSource
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dataSource: LocalTransactionDataSource,
    mapper: TransactionMapper
) : TransactionRepository {
    override suspend fun findAll(): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllLimit1(): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByTypeAndAccount(
        type: TransactionType,
        accountId: AccountId
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByTypeAndAccountBetween(
        type: TransactionType,
        accountId: AccountId,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllTransfersToAccount(
        toAccountId: AccountId,
        type: TransactionType
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByAccountAndBetween(
        accountId: AccountId,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByCategoryAndBetween(
        categoryId: CategoryId,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllUnspecifiedAndBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByCategoryAndTypeAndBetween(
        categoryId: CategoryId,
        type: TransactionType,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllUnspecifiedAndTypeAndBetween(
        type: TransactionType,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllToAccountAndBetween(
        toAccountId: AccountId,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllDueToBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllDueToBetweenByCategory(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        categoryId: CategoryId
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllDueToBetweenByCategoryUnspecified(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllDueToBetweenByAccount(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        accountId: AccountId
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByRecurringRuleId(recurringRuleId: UUID): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: TransactionId): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun countHappenedTransactions(): Long {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByTitleMatchingPattern(pattern: String): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun countByTitleMatchingPattern(pattern: String): Long {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByCategory(categoryId: CategoryId): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun countByTitleMatchingPatternAndCategoryId(
        pattern: String,
        categoryId: CategoryId
    ): Long {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByAccount(accountId: AccountId): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun countByTitleMatchingPatternAndAccountId(
        pattern: String,
        accountId: AccountId
    ): Long {
        TODO("Not yet implemented")
    }

    override suspend fun findLoanTransaction(loanId: UUID): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun findLoanRecordTransaction(loanRecordId: UUID): Transaction? {
        TODO("Not yet implemented")
    }

    override suspend fun findAllByLoanId(loanId: UUID): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun save(value: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun saveMany(value: List<Transaction>) {
        TODO("Not yet implemented")
    }

    override suspend fun flagDeleted(id: TransactionId) {
        TODO("Not yet implemented")
    }

    override suspend fun flagDeletedByRecurringRuleIdAndNoDateTime(recurringRuleId: UUID) {
        TODO("Not yet implemented")
    }

    override suspend fun flagDeletedByAccountId(accountId: AccountId) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: TransactionId) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllByAccountId(accountId: AccountId) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}