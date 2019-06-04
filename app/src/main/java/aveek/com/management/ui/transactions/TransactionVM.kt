package aveek.com.management.ui.transactions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import aveek.com.management.db.entity.Transaction
import aveek.com.management.db.repository.DatabaseRepository
import io.reactivex.Single
import javax.inject.Inject

class TransactionVM @Inject constructor(val repository: DatabaseRepository): ViewModel() {

    val transactionIcon  = ObservableField<String>()
    val transactionTitle  = ObservableField<String>()
    val creditValue  = ObservableField<String>()
    val transactionTime  = ObservableField<String>()
    val dividerVisibility = ObservableInt()
    val paymentType = ObservableField<String>()
    val transactionCategory = ObservableField<String>()

    fun loadTransactions() : Single<List<Transaction>>{
        return repository.getAllTransactions()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe()
    }
}