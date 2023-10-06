package com.ivy.data.source

import com.ivy.base.model.TransactionType
import com.ivy.data.db.dao.read.TransactionDao
import com.ivy.data.db.dao.write.WriteTransactionDao
import com.ivy.data.db.entity.TransactionEntity
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class LocalTransactionDataSource @Inject constructor(
    private val transactionDao: TransactionDao,
    private val writeTransactionDao: WriteTransactionDao
) {
    suspend fun findAll(): List<TransactionEntity> {
        return transactionDao.findAll()
    }

    suspend fun findAllLimit1(): List<TransactionEntity> {
        return transactionDao.findAll_LIMIT_1()
    }

    suspend fun findAllByType(type: TransactionType): List<TransactionEntity> {
        return transactionDao.findAllByType(type)
    }

    suspend fun findAllByTypeAndAccount(
        type: TransactionType,
        accountId: UUID
    ): List<TransactionEntity> {
        return transactionDao.findAllByTypeAndAccount(type, accountId)
    }

    suspend fun findAllByTypeAndAccountBetween(
        type: TransactionType,
        accountId: UUID,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllByTypeAndAccountBetween(type, accountId, startDate, endDate)
    }

    suspend fun findAllTransfersToAccount(
        toAccountId: UUID,
        type: TransactionType = TransactionType.TRANSFER
    ): List<TransactionEntity> {
        return transactionDao.findAllTransfersToAccount(toAccountId, type)
    }

    suspend fun findAllBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllBetween(startDate, endDate)
    }

    suspend fun findAllByAccountAndBetween(
        accountId: UUID,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllByAccountAndBetween(accountId, startDate, endDate)
    }

    suspend fun findAllByCategoryAndBetween(
        categoryId: UUID,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllByCategoryAndBetween(categoryId, startDate, endDate)
    }

    suspend fun findAllUnspecifiedAndBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllUnspecifiedAndBetween(startDate, endDate)
    }

    suspend fun findAllByCategoryAndTypeAndBetween(
        categoryId: UUID,
        type: TransactionType,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllByCategoryAndTypeAndBetween(
            categoryId,
            type,
            startDate,
            endDate
        )
    }

    suspend fun findAllUnspecifiedAndTypeAndBetween(
        type: TransactionType,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllUnspecifiedAndTypeAndBetween(type, startDate, endDate)
    }

    suspend fun findAllToAccountAndBetween(
        toAccountId: UUID,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllToAccountAndBetween(toAccountId, startDate, endDate)
    }

    suspend fun findAllDueToBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<TransactionEntity> {
        return transactionDao.findAllDueToBetween(startDate, endDate)
    }

    suspend fun findAllDueToBetweenByCategory(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        categoryId: UUID
    ): List<TransactionEntity> {
        return transactionDao.findAllDueToBetweenByCategory(startDate, endDate, categoryId)
    }

    suspend fun findAllDueToBetweenByCategoryUnspecified(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<TransactionEntity> {
        return transactionDao.findAllDueToBetweenByCategoryUnspecified(startDate, endDate)
    }

    suspend fun findAllDueToBetweenByAccount(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        accountId: UUID
    ): List<TransactionEntity> {
        return transactionDao.findAllDueToBetweenByAccount(startDate, endDate, accountId)
    }

    suspend fun findAllByRecurringRuleId(recurringRuleId: UUID): List<TransactionEntity> {
        return transactionDao.findAllByRecurringRuleId(recurringRuleId)
    }

    suspend fun findById(id: UUID): TransactionEntity? {
        return transactionDao.findById(id)
    }

    suspend fun countHappenedTransactions(): Long {
        return transactionDao.countHappenedTransactions()
    }

    suspend fun findAllByTitleMatchingPattern(pattern: String): List<TransactionEntity> {
        return transactionDao.findAllByTitleMatchingPattern(pattern)
    }

    suspend fun countByTitleMatchingPattern(pattern: String): Long {
        return transactionDao.countByTitleMatchingPattern(pattern)
    }

    suspend fun findAllByCategory(categoryId: UUID): List<TransactionEntity> {
        return transactionDao.findAllByCategory(categoryId)
    }

    suspend fun countByTitleMatchingPatternAndCategoryId(
        pattern: String,
        categoryId: UUID
    ): Long {
        return transactionDao.countByTitleMatchingPatternAndCategoryId(pattern, categoryId)
    }

    suspend fun findAllByAccount(accountId: UUID): List<TransactionEntity> {
        return transactionDao.findAllByAccount(accountId)
    }

    suspend fun countByTitleMatchingPatternAndAccountId(pattern: String, accountId: UUID): Long {
        return transactionDao.countByTitleMatchingPatternAndAccountId(pattern, accountId)
    }

    suspend fun findLoanTransaction(loanId: UUID): TransactionEntity? {
        return transactionDao.findLoanTransaction(loanId)
    }

    suspend fun findLoanRecordTransaction(loanRecordId: UUID): TransactionEntity? {
        return transactionDao.findLoanRecordTransaction(loanRecordId)
    }

    suspend fun findAllByLoanId(loanId: UUID): List<TransactionEntity> {
        return transactionDao.findAllByLoanId(loanId)
    }
}