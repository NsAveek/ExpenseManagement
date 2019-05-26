package aveek.com.management.ui.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import aveek.com.management.BaseApp
import aveek.com.management.ui.db.dao.TransactionDAO
import aveek.com.management.ui.db.entity.Transaction

@Database(entities=[Transaction::class], version=1)
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