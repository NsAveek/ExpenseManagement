package aveek.com.management.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class OperationsBottomSheetViewModel : ViewModel() {

    val addData = MutableLiveData<Boolean>().apply { value = false }
    val dismissData = MutableLiveData<Boolean>().apply { value = false }

    fun add(){
        // TODO : add data from the user input and pass to the fragment
        addData.value = true
        dismissData.value = false
    }
    fun dismiss(){
        // TODO : send dismiss notification
        addData.value = false
        dismissData.value = true
    }
}
