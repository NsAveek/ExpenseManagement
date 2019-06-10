package aveek.com.management.db.repository

import aveek.com.management.db.dao.TransactionDAO
import aveek.com.management.db.entity.Transaction
import io.reactivex.Single
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: TransactionDAO): IRepository {

    override fun getTransactions(lastIndex : Int, pageSize : Int) : Single<List<Transaction>>{
        return dao.getTransactions(lastIndex,pageSize)
    }

    override fun getAllTransactions(): Single<List<Transaction>> {
        return dao.getAllTransactions()
    }

    override fun saveTransaction(transaction: Transaction) {
        dao.insert(transaction)
    }
}