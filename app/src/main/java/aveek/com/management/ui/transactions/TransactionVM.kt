package aveek.com.management.ui.transactions

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import javax.inject.Inject

class TransactionVM : ViewModel() {

    val transactionIcon  = ObservableField<String>()
    val transactionTitle  = ObservableField<String>()
    val creditValue  = ObservableField<String>()
    val transactionTime  = ObservableField<String>()
    val dividerVisibility = ObservableInt()
    val paymentType = ObservableField<String>()
    val transactionCategory = ObservableField<String>()
}