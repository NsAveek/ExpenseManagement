package aveek.com.management.db.repository

import aveek.com.management.db.dao.TransactionDAO
import aveek.com.management.db.entity.Transaction
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: TransactionDAO): IRepository {

    override fun saveTransaction(transaction: Transaction) {
        dao.insert(transaction)
    }
}