package aveek.com.management.ui.home


import android.arch.lifecycle.*
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import aveek.com.management.R
import aveek.com.management.databinding.ActivityMainBinding
import aveek.com.management.di.Injectable
import aveek.com.management.ui.common.NetworkActivity
import aveek.com.management.ui.home.categories.CategoriesFragment
import aveek.com.management.ui.home.main.MainFragment
import aveek.com.management.ui.home.operation.OperationsBottomSheetFragment
import aveek.com.management.ui.home.operation.OperationsBottomSheetViewModel
import aveek.com.management.ui.transactions.TransactionActivity
import aveek.com.management.util.EnumDataState
import aveek.com.management.util.EnumEventState
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector

class MainActivity : NetworkActivity(), LifecycleOwner, HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var binding : ActivityMainBinding

    private lateinit var compositeDisposable : CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainActivityViewModel::class.java)



//        compositeDisposable = CompositeDisposable()
//
//        initBinding()
//
        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        initFragment()

//
//
//        with(binding){
//            this.viewmodel?.let { localViewModel ->
//                with(localViewModel) {
//                    balanceText.set("Aveek testing")
//                    creditData.observe(this@MainActivity, Observer {
//                        it?.let {
//                            if (it) {
//                                addExpenseOperation()
//                            }
//                        }
//                    })
//                    transactionHistory.observe(this@MainActivity, Observer {
//                        it?.let {
//                            // TODO : Generate History list
//                            if (it) {
//                                loadTransactionHistory()
//                            }
//                        }
//                    })
//                    category.observe(this@MainActivity, Observer {
//                        it?.let {
//                            // TODO : Generate Category
//                            if (it) {
//                                getCategoriesOperation()
//                            }
//                        }
//                    })
//                    expense.observe(this@MainActivity, Observer {
//                        it?.let {
//                            // TODO : Generate Expense
//                            if (it) {
//                                getExpenseListOperation()
//                            }
//                        }
//                    })
//                }
//            }
//        }
    }

    private fun initFragment() {
        val mainFragment = MainFragment.newInstance()
        supportFragmentManager
                .beginTransaction()
                // 2
                .replace(R.id.fragment_holder, mainFragment, "main")
                // 3
                .addToBackStack(null)
                .commit()
    }

    private fun addExpenseOperation() {
        OperationsBottomSheetFragment.getOperationsBottomSheetFragment().apply {
            isCancelable = false
            show(supportFragmentManager, null)
            dismissOrProceedEvent.observe(this@MainActivity, Observer {
                it?.let { pair ->
                    when (pair.first.type) {
                        EnumEventState.PROCEED.type -> {
                            with(pair.second){
                                with(this as Pair<EnumDataState, Any>){
                                    when(this.first.type){
                                        EnumDataState.SUCCESS.type -> {
                                            onSuccess()
                                        }
                                        EnumDataState.ERROR.type -> {
                                            val throwable = this.second as Throwable
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
//
    private fun loadTransactionHistory() {
        Intent(this,TransactionActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun getCategoriesOperation() {
        //TODO : Make a Main activity with fragment, then replace the fragments
        CategoriesFragment.newInstance().also {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.show(it)
        }
    }

    private fun getExpenseListOperation(){
        // TODO : Add Expense Fragment
    }
//
//    private fun initBinding() {
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.viewmodel = viewModel
//        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
//    }

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

    private fun onSuccess(){
        Toast.makeText(this, "Successfully inserted",Toast.LENGTH_LONG).show()
    }

//    override fun getLifecycle(): Lifecycle {
//        return mLifecycleRegistry
//    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}
