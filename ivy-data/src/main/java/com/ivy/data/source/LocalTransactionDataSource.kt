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

    suspend fun findAllTransfersToAccountBetween(
        toAccountId: UUID,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        type: TransactionType = TransactionType.TRANSFER
    ): List<TransactionEntity> {
        return transactionDao.findAllTransfersToAccountBetween(
            toAccountId,
            startDate,
            endDate,
            type
        )
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
}