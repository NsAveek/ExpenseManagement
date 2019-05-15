package aveek.com.management.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class OperationsBottomSheetViewModel : ViewModel() {

    val addData = MutableLiveData<Boolean>().apply { value = false }
    val dismissData = MutableLiveData<Boolean>().apply { value = false }

    fun add(){
        // TODO : add data from the user input and pass to the fragment

        // TODO : Add Text watcher for Edit Text
        // TODO : On add () function call, get all data from the fragment
        // TODO : Move all of the operation code from the activity ( i.e : Database operation )
        addData.value = true
        dismissData.value = false


    }
    fun dismissBottomSheet(){
        addData.value = false
        dismissData.value = true
    }
}
