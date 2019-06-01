package aveek.com.management.ui.home.operation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import aveek.com.management.repository.DatabaseRepository
import aveek.com.management.ui.db.entity.Transaction
import aveek.com.management.util.EnumDataState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class OperationsBottomSheetViewModel @Inject constructor() : ViewModel() {

    private val addData = MutableLiveData<Boolean>().apply { value = false }
    private val dismissData = MutableLiveData<Boolean>().apply { value = false }
    private val transaction = MutableLiveData<Transaction>()
    private val amount = ObservableField<String>().apply { set("") }
    private val purpose = ObservableField<String>().apply { set("") }
    private val date = ObservableField<String>().apply { set("") }

    private lateinit var compositeDisposable : CompositeDisposable

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
    fun addTransactionData() : MutableLiveData<Pair<EnumDataState,Any>>{


        // TODO : addTransactionData data from the user input and pass to the fragment
        // TODO : Add Text watcher for Edit Text
        // TODO : On addTransactionData () function call, get all data from the fragment
        // TODO : Move all of the operation code from the activity ( i.e : Database operation )

        val data = MutableLiveData<Pair<EnumDataState,Any>>()
        val transactionModel = Transaction(UUID.randomUUID().toString(), "credit", "shopping", purpose.get(), amount.get()!!.toDouble(), date.get())
        compositeDisposable.add(
            Completable.fromAction {
//                repository.saveTransaction(transactionModel)
            }.subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe({
                 data.value = Pair(EnumDataState.SUCCESS,transactionModel)
             },{
                 data.value = Pair(EnumDataState.ERROR,it)
             })
        )
        return data
    }


    fun dismissBottomSheet(){
        addData.value = false
        dismissData.value = true
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
