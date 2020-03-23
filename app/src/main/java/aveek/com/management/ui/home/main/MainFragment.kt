package aveek.com.management.ui.home.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aveek.com.management.R
import aveek.com.management.databinding.MainFragmentBinding
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.util.EnumEventOperations
import aveek.com.management.util.EventMessage
import aveek.com.management.util.RxBus
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainFragmentViewModel

    private lateinit var binding: MainFragmentBinding

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



        initBinding(inflater,container!!)

//        initialValueSetup()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        with(binding) {
            this.viewmodel?.let { localViewModel ->
                with(localViewModel) {
                    balanceText.set("Aveek testing")
                    creditData.observe(this@MainFragment, Observer {
                        it?.let {
                            if (it) {
                                addExpenseOperation()
                            }
                        }
                    })
                    transactionHistory.observe(this@MainFragment, Observer {
                        it?.let {
                            // TODO : Generate History list
                            if (it) {
                                loadTransactionHistory()
                            }
                        }
                    })
                    getCategory().observe(this@MainFragment, Observer {
                        it?.let {
                            // TODO : Find out alternatives
                            it.getContentIfNotHandled()?.let {
                                getCategoriesOperation()
                            }
                        }
                    })
                    expense.observe(this@MainFragment, Observer {
                        it?.let {
                            // TODO : Generate Expense
                            if (it) {
                                getExpenseListOperation()
                            }
                        }
                    })
                }
            }
        }

//        return inflater.inflate(R.layout.main_fragment, container, false)
        return binding.root
    }


    private fun initBinding(inflater : LayoutInflater,
                            container : ViewGroup) {
        binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this // To enable Live Data object to update the XML on update
    }

    /* EventBus example for communicating between Fragment - Activity


    private fun addExpenseOperation(){
        EventBus.getDefault().post(EventMessage("Fire fragment", EnumEventOperations.INCOME))
    }

    private fun loadTransactionHistory() {
        EventBus.getDefault().post(EventMessage("Fire activity", EnumEventOperations.TRANSACTIONLIST))
    }

    private fun getCategoriesOperation() {
        EventBus.getDefault().post(EventMessage("Fire fragment", EnumEventOperations.CATEGORIES))
    }

    private fun getExpenseListOperation(){
        EventBus.getDefault().post(EventMessage("Fire fragment", EnumEventOperations.EXPENSES))
    }
 */

    private fun addExpenseOperation() {
        RxBus.publish(EnumEventOperations.INCOME)
    }

    private fun loadTransactionHistory() {
        RxBus.publish(EnumEventOperations.TRANSACTIONLIST)
    }

    private fun getCategoriesOperation() {
        RxBus.publish(EnumEventOperations.CATEGORIES)
    }

    private fun getExpenseListOperation() {
        RxBus.publish(EnumEventOperations.EXPENSES)
    }
}
