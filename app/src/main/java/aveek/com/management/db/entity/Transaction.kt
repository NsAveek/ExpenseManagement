package aveek.com.management.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class Transaction (
        @PrimaryKey var uid : String,
        @ColumnInfo(name = "payment_type") var paymentType : String?, // Cash or Card or Bank transaction
        @ColumnInfo(name = "category") var category : String?, // Category of the income, i.e : Salary
        @ColumnInfo(name = "purpose") var purpose : String?, // Note on the transaction
        @ColumnInfo(name = "amount") var amount : Double?, // Amount to be credited
        @ColumnInfo(name = "date") var date : String?, // Date to addTransactionData the transaction
        @Ignore var type : String? = null
){
    constructor():this("","","","",0.0,"","") // To avoid error with @Ignore annotation
}

