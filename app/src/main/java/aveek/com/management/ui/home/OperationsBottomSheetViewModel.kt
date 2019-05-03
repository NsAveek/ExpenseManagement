package aveek.com.management.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;

class OperationsBottomSheetViewModel : ViewModel() {
    fun add() : MutableLiveData<Boolean>{
        // TODO : add data from the user input and pass to the fragment
        val data : MutableLiveData<Boolean> = MutableLiveData()
            data.value = true
        return data
    }
    fun dismiss(): MutableLiveData<Boolean>{
        // TODO : send dismiss notification
        val data : MutableLiveData<Boolean> = MutableLiveData()
            data.value = true
        return data
    }
}
