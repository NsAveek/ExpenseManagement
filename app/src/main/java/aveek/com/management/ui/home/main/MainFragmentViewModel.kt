package aveek.com.management.ui.home.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField
import aveek.com.management.util.CustomEventLiveData
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor() : ViewModel(){

    private var creditData = MutableLiveData<CustomEventLiveData<Boolean>>()
    private var transactionHistory = MutableLiveData<CustomEventLiveData<Boolean>>()
    private var category = MutableLiveData<CustomEventLiveData<Boolean>> ()
    private var expense = MutableLiveData<CustomEventLiveData<Boolean>>()

    fun getCategory(): MutableLiveData<CustomEventLiveData<Boolean>> {
        return category
    }
    fun getTransactionHistory(): MutableLiveData<CustomEventLiveData<Boolean>> {
        return transactionHistory
    }
    fun getCreditData(): MutableLiveData<CustomEventLiveData<Boolean>> {
        return creditData
    }
    fun getExpense(): MutableLiveData<CustomEventLiveData<Boolean>> {
        return expense
    }
    val balanceText = ObservableField<String>()

    fun clickCreditData(){
        creditData.value = CustomEventLiveData(true)
    }
    fun clickTransactionHistory(){
        transactionHistory.value = CustomEventLiveData(true)
    }
    fun clickCategory(){
        category.value = CustomEventLiveData(true)
    }
    fun clickExpense(){
        expense.value = CustomEventLiveData(true)
    }
}
