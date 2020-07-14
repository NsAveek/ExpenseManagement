package aveek.com.management.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(): ViewModel() {
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
//class CounterViewModel: ViewModel() {
//    private val _liveData = MutableLiveData<Int>()
//
//    val liveData: LiveData<Int> = _liveData
//
//    init {
//        _liveData.value = 0
//    }
//
//    fun increment() {
//        _liveData.value = _liveData.value!! + 1
//    }
//
//    fun decrement() {
//        _liveData.value = _liveData.value!! - 1
//    }
//}

