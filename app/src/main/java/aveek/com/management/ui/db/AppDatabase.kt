package aveek.com.management.ui.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import aveek.com.management.ui.db.dao.TransactionDAO
import aveek.com.management.ui.db.entity.Transaction

@Database(entities=[Transaction::class], version=1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDAO
}