package aveek.com.management.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import aveek.com.management.repository.DatabaseRepository
import javax.inject.Inject


class MainActivityViewModel : ViewModel() {
    val creditData = MutableLiveData<Boolean>()
    val transactionHistory = MutableLiveData<Boolean>()
    val category = MutableLiveData<Boolean>()
    val expense = MutableLiveData<Boolean>()

    val balanceText = ObservableField<String>()

    fun clickCreditData(){
        creditData.value = true
    }
    fun clickTransactionHistory(){
        transactionHistory.value = true
    }
    fun clickCategory(){
        category.value = true
    }
    fun clickExpense(){
        expense.value = true
    }

}