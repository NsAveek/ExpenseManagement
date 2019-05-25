package aveek.com.management.ui.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import aveek.com.management.ui.db.entity.Transaction
import io.reactivex.Single

@Dao
interface TransactionDAO  {

    @Insert
    fun insert(transaction : Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("SELECT * FROM `transaction`")
    fun getAllTransactions() : Single<List<Transaction>>
//    fun getAllTransactions() : Single<List<TransactionCopy>>

}