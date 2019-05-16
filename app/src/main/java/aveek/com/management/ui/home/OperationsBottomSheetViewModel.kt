package aveek.com.management.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import aveek.com.management.ui.db.entity.Transaction

class OperationsBottomSheetViewModel : ViewModel() {

    val addData = MutableLiveData<Boolean>().apply { value = false }
    val dismissData = MutableLiveData<Boolean>().apply { value = false }
    val amount = ObservableField<String>().apply { set("") }
    val purpose = ObservableField<String>().apply { set("") }
    val date = ObservableField<String>().apply { set("") }

    fun add(){
        // TODO : add data from the user input and pass to the fragment

        // TODO : Add Text watcher for Edit Text
        // TODO : On add () function call, get all data from the fragment
        // TODO : Move all of the operation code from the activity ( i.e : Database operation )
        addData.value = true
        dismissData.value = false

        val checkamount = amount.get()
        val checkpurpose = purpose.get()
        val checkdate = date.get()
        val coll = "$checkamount $checkpurpose $checkdate "
    }
    fun dismissBottomSheet(){
        addData.value = false
        dismissData.value = true
    }
}
