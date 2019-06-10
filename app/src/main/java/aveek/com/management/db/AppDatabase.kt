package aveek.com.management.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import aveek.com.management.db.dao.TransactionDAO
import aveek.com.management.db.entity.Transaction

@Database(entities=[Transaction::class], version=1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDAO
//    companion object {
//        var INSTANCE: AppDatabase? = null
//
//        fun getAppDataBase(): AppDatabase? {
//            if (INSTANCE == null){
//                synchronized(AppDatabase::class){
//                    INSTANCE = Room.databaseBuilder(BaseApp.baseApp, AppDatabase::class.java, "myDB").build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyDataBase(){
//            INSTANCE = null
//        }
//    }
}