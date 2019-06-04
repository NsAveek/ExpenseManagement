package aveek.com.management.db.repository

import aveek.com.management.db.entity.Transaction
import io.reactivex.Single

interface IRepository {
    fun saveTransaction(transaction: Transaction)
    fun getAllTransactions() : Single<List<Transaction>>
}