package aveek.com.management.db.repository

import aveek.com.management.db.entity.Transaction

interface IRepository {
    fun saveTransaction(transaction: Transaction)
}