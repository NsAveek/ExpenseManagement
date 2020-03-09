package aveek.com.management.ui.home


import android.arch.lifecycle.*
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import aveek.com.management.R
import aveek.com.management.databinding.ActivityMainBinding
import aveek.com.management.ui.common.NetworkActivity
import aveek.com.management.ui.home.categories.CategoriesFragment
import aveek.com.management.ui.home.expense.ExpenseFragment
import aveek.com.management.ui.home.main.MainFragment
import aveek.com.management.ui.home.operation.OperationsBottomSheetFragment
import aveek.com.management.ui.transactions.TransactionActivity
import aveek.com.management.util.EnumDataState
import aveek.com.management.util.EnumEventOperations.*
import aveek.com.management.util.EnumEventState
import aveek.com.management.util.RxBus
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainActivity : NetworkActivity(), LifecycleOwner, HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var binding: ActivityMainBinding

    private lateinit var compositeDisposable: CompositeDisposable

    private var testRxCall : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        viewModel=ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        compositeDisposable = CompositeDisposable()


        initBinding()

        mLifecycleRegistry=LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        if(savedInstanceState == null){
            initFragment()
            initRx()
        }
    }

    private fun initBinding() {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel=viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }

    private fun initFragment() {
        supportFragmentManager.inTransaction {
//            add(R.id.fragment_holder, MainFragment.newInstance()).addToBackStack("main")
            add(R.id.fragment_holder, MainFragment.newInstance()).addToBackStack("main")
        }
    }

    private fun replaceFragment(fragment: Fragment, name : String){
        this.supportFragmentManager.inTransaction {
//            initBinding()
            replace(R.id.fragment_holder,fragment)
                    .addToBackStack(name)
        }
    }

    // Higher Order Function Kotlin Example
    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    private fun initRx(){
        compositeDisposable.add(
                RxBus.listen()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ enumEventOperations ->
                            if (!testRxCall){
                                when(enumEventOperations){

                                    // TODO : We have noticed that this subscription is getting called every time on popbackstack.
                                    // TODO : Question : Why Rx is getting called here again and again?

                                    INCOME -> addExpenseOperation()
                                    TRANSACTIONLIST -> loadTransactionHistory()
                                    CATEGORIES -> getCategoriesOperation()
                                    EXPENSES -> getExpenseListOperation()
                                }
                            }
                },{
                    onError(it.message)
                }))
    }


    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
//        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
//        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
        compositeDisposable.dispose()
    }

    private fun onSuccess() {
        Toast.makeText(this, "Successfully inserted", Toast.LENGTH_LONG).show()
    }

//    @Subscribe(threadMode=ThreadMode.MAIN)
//    fun onMessage(event: EventMessage) {
//        with(event){
////            initBinding()
//            when(getEvents().second)
//            {
//                INCOME -> addExpenseOperation()
//                TRANSACTIONLIST -> loadTransactionHistory()
//                CATEGORIES -> getCategoriesOperation()
//                EXPENSES -> getExpenseListOperation()
//            }
//        }
//    }

    //Fragment - Activity - Fragment
    private fun addExpenseOperation() {
        OperationsBottomSheetFragment.getOperationsBottomSheetFragment().apply {
            isCancelable=false
            show(supportFragmentManager, null)
            dismissOrProceedEvent.observe(this@MainActivity, Observer {
                it?.let { pair ->
                    when (pair.first.type) {
                        EnumEventState.PROCEED.type -> {
                            with(pair.second) {
                                with(this as Pair<EnumDataState, Any>) {
                                    when (this.first.type) {
                                        EnumDataState.SUCCESS.type -> {
                                            onSuccess()
                                        }
                                        EnumDataState.ERROR.type -> {
                                            val throwable=this.second as Throwable
                                            onError(throwable.message)
                                        }
                                        else -> {
                                            onError("Something went wrong")
                                        }
                                    }
                                }
                            }
                        }
                        EnumEventState.DISMISS.type -> {
                            dismiss() // Redundant dismiss
                        }
                        else -> {
                            dismiss() // OperationsBottomSheet already have dismiss() command
                        }
                    }
                }
            })
        }
    }

    //Fragment - Activity - Activity
    private fun loadTransactionHistory() {
        Intent(this, TransactionActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun getCategoriesOperation() {
        testRxCall = true
        replaceFragment(CategoriesFragment.newInstance(), CategoriesFragment.name)
    }

    private fun getExpenseListOperation() {
        replaceFragment(ExpenseFragment.newInstance(), ExpenseFragment.name)
    }

//    override fun getLifecycle(): Lifecycle {
//        return mLifecycleRegistry
//    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount >0){
//            if(supportFragmentManager.backStackEntryCount ==1){
//                supportFragmentManager.inTransaction {
//                    add(R.id.fragment_holder, MainFragment.newInstance()).addToBackStack("main")
//                }
//            }else {
                this.supportFragmentManager.popBackStack()
//                this.supportFragmentManager.popBackStackImmediate(CategoriesFragment.name, 1)

//            }
        } else{
            super.onBackPressed()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}

