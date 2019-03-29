package aveek.com.management.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    val data = MutableLiveData<Boolean>()
    val balanceText = ObservableField<String>()
    fun clickData(){
        data.value = false
    }
}