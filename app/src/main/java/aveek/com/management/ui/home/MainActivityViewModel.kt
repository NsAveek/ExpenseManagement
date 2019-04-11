package aveek.com.management.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField


class MainActivityViewModel : ViewModel() {
    val data = MutableLiveData<Boolean>()
    val balanceText = ObservableField<String>()
    fun clickData(){
        data.value = false
    }
    fun clickTransactionHistory(){

    }
    fun clickCategory(){

    }
}