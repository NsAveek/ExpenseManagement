package aveek.com.management.db.repository

import aveek.com.management.db.AppDatabase
import aveek.com.management.db.entity.Transaction
import javax.inject.Inject

class DatabaseRepository @Inject constructor(val database : AppDatabase): IRepository {

    override fun saveTransaction(transaction: Transaction) {
        database.transactionDao().insert(transaction)
    }
}