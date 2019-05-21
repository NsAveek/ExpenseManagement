package aveek.com.management.ui.home


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import aveek.com.management.BaseActivity
import aveek.com.management.R
import aveek.com.management.databinding.ActivityMainBinding
import aveek.com.management.ui.common.NetworkActivity
import aveek.com.management.ui.db.AppDatabase
import aveek.com.management.ui.db.entity.Transaction
import aveek.com.management.ui.home.operation.OperationsBottomSheetFragment
import aveek.com.management.ui.transactions.TransactionActivity
import aveek.com.management.util.EnumEventState
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector

class MainActivity : NetworkActivity(), LifecycleOwner, HasSupportFragmentInjector {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var binding : ActivityMainBinding

    // TODO : Inject Database
    private lateinit var database : AppDatabase

    private lateinit var compositeDisposable : CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()

        initDatabase()

        initBinding()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        with(binding){
            this.viewmodel?.let { localViewModel ->
                with(localViewModel) {
                    balanceText.set("Aveek testing")
                    creditData.observe(this@MainActivity, Observer {
                        it?.let {
                            if (it) {
                                addExpenseOperation()
                            }
                        }
                    })
                    transactionHistory.observe(this@MainActivity, Observer {
                        it?.let {
                            // TODO : Generate History list
                            if (it) {
                                loadTransactionHistory()
                            }
                        }
                    })
                    category.observe(this@MainActivity, Observer {
                        it?.let {
                            // TODO : Generate Category
                            if (it) {
                                getCategoriesOperation()
                            }
                        }
                    })
                    expense.observe(this@MainActivity, Observer {
                        it?.let {
                            // TODO : Generate Expense
                            if (it) {
                                Toast.makeText(this@MainActivity, " Expense ", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        }
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    private fun getCategoriesOperation() {
        Toast.makeText(this@MainActivity, " Category ", Toast.LENGTH_SHORT).show()
    }

    private fun loadTransactionHistory() {
        Intent(this,TransactionActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun addExpenseOperation() {
        OperationsBottomSheetFragment.getOperationsBottomSheetFragment().apply {
            isCancelable = false
            show(supportFragmentManager, null)
            dismissOrProceedEvent.observe(this@MainActivity, Observer {
                it?.let { pair ->
                    when (pair.first.type) {
                        EnumEventState.PROCEED.type -> {
                        compositeDisposable.add(Completable.fromAction {
                                Action {
                                    suspend {
                                        // TODO : Add the received data from the Bottom Sheet Fragment
                                        database.transactionDao().insert(Transaction(UUID.randomUUID().toString(), "credit", "shopping", "DIY", 25.50, "2019-04-11"))
                                    }
                                }
                            }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this@MainActivity::onSuccess, this@MainActivity::onError))
                        }
                        EnumEventState.DISMISS.type -> {
                            dismiss()
                        }
                        else -> {
                            dismiss()
                        }
                    }
                }
            })
        }
    }

    private fun initDatabase() {
//        database = Room.databaseBuilder(
//                applicationContext,
//                AppDatabase::class.java, "expense.db"
//        ).build()
        database = AppDatabase.getAppDataBase(this)!!
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
        compositeDisposable.dispose()
    }


//    override fun fragmentInjector(): AndroidInjector<Fragment> {
//        return fragmentInjector
//    }

    private fun onSuccess(){
        Toast.makeText(this, "Successfully inserted",Toast.LENGTH_LONG).show()
    }




//    override fun getLifecycle(): Lifecycle {
//        return mLifecycleRegistry
//    }
}
