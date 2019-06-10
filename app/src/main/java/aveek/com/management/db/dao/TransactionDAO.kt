package aveek.com.management.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import aveek.com.management.db.entity.Transaction
import io.reactivex.Single

@Dao
interface TransactionDAO  {

    @Insert
    fun insert(transaction : Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Query("SELECT * FROM `transaction`")
    fun getAllTransactions() : Single<List<Transaction>>

    @Query("SELECT * FROM `transaction` LIMIT :pageSize OFFSET :lastIndex")
    fun getTransactions(lastIndex : Int, pageSize : Int) : Single<List<Transaction>>
}