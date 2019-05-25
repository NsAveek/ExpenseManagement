package aveek.com.management.ui.home.operation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import aveek.com.management.ui.db.entity.Transaction
import java.util.*

class OperationsBottomSheetViewModel : ViewModel() {

    private val addData = MutableLiveData<Boolean>().apply { value = false }
    private val dismissData = MutableLiveData<Boolean>().apply { value = false }
    private val transaction = MutableLiveData<Transaction>()
    private val amount = ObservableField<String>().apply { set("") }
    private val purpose = ObservableField<String>().apply { set("") }
    private val date = ObservableField<String>().apply { set("") }

    fun getDismissCommand() : MutableLiveData<Boolean>{
        return dismissData
    }

    fun getAmount() : ObservableField<String>{
        return amount
    }

    fun getPurpose() : ObservableField<String>{
        return purpose
    }

    fun getDate(): ObservableField<String>{
        return date
    }

    fun getTransaction() : MutableLiveData<Transaction> {
        return transaction
    }
    fun addTransactionData(){


        // TODO : addTransactionData data from the user input and pass to the fragment
        // TODO : Add Text watcher for Edit Text
        // TODO : On addTransactionData () function call, get all data from the fragment
        // TODO : Move all of the operation code from the activity ( i.e : Database operation )

        dismissData.value = false
        transaction.value = Transaction(UUID.randomUUID().toString(), "credit", "shopping", purpose.get(), amount.get()!!.toDouble(), date.get())

    }
    fun dismissBottomSheet(){
        addData.value = false
        dismissData.value = true
    }
}
