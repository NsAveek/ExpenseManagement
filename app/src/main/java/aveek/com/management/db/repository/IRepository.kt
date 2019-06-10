package aveek.com.management.db.repository

import aveek.com.management.db.entity.Transaction
import io.reactivex.Single

interface IRepository {
    fun saveTransaction(transaction: Transaction)
    fun getTransactions(lastIndex : Int, pageSize : Int) : Single<List<Transaction>>
    fun getAllTransactions() : Single<List<Transaction>>
}