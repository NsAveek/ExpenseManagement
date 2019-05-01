package aveek.com.management.ui.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import aveek.com.management.ui.db.entity.Transaction
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TransactionDAO  {

    @Insert
    fun insert(transaction : Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("SELECT * FROM `transaction`")
//    fun getAllTransactions() : LiveData<List<Transaction>>
    fun getAllTransactions() : Single<List<Transaction>>

}