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
import aveek.com.management.ui.home.main.MainFragment
import aveek.com.management.ui.home.operation.OperationsBottomSheetFragment
import aveek.com.management.ui.transactions.TransactionActivity
import aveek.com.management.util.EnumDataState
import aveek.com.management.util.EnumEventOperations
import aveek.com.management.util.EnumEventState
import aveek.com.management.util.EventMessage
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        viewModel=ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

//        compositeDisposable = CompositeDisposable()
//
        initBinding()

        mLifecycleRegistry=LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        initFragment()
    }


    // Higher Order Function Kotlin Example
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    private fun initBinding() {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel=viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }

    private fun initFragment() {
        supportFragmentManager.inTransaction {
            add(R.id.fragment_holder, MainFragment.newInstance())
        }
    }

    private fun replaceFragment(fragment: Fragment){

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, fragment).commit()
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
//        ft.commit()


//        supportFragmentManager.inTransaction {
//            replace(R.id.fragment_holder,fragment)
//        }
    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
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

    @Subscribe(threadMode=ThreadMode.MAIN)
    fun onMessage(event: EventMessage) {
        with(event){
            when(getEvents().second)
            {
                EnumEventOperations.INCOME -> addExpenseOperation()
                EnumEventOperations.TRANSACTIONLIST -> loadTransactionHistory()
                EnumEventOperations.CATEGORIES -> getCategoriesOperation()
                EnumEventOperations.EXPENSES -> getExpenseListOperation()
            }
        }
    }

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
        replaceFragment(CategoriesFragment.newInstance())
    }

    private fun getExpenseListOperation() {
        // TODO : Add Expense Fragment
    }

//    override fun getLifecycle(): Lifecycle {
//        return mLifecycleRegistry
//    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}
