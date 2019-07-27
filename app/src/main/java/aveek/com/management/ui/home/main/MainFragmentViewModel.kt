package aveek.com.management.ui.home.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor() : ViewModel(){

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
