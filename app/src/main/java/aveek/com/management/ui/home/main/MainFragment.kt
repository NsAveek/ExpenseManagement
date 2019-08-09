package aveek.com.management.ui.home.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aveek.com.management.R
import aveek.com.management.databinding.MainFragmentBinding
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.util.EventMessage
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainFragmentViewModel

    private lateinit var binding : MainFragmentBinding

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var compositeDisposable : CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel=ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)

        compositeDisposable = CompositeDisposable()

        initBinding()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        with(binding){
            this.viewmodel?.let { localViewModel ->
                kotlin.with(localViewModel) {
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
                    category.observe(this@MainFragment, Observer {
                        it?.let {
                            // TODO : Generate Category
                            if (it) {
//                                getCategoriesOperation()
                            }
                        }
                    })
                    expense.observe(this@MainFragment, Observer {
                        it?.let {
                            // TODO : Generate Expense
                            if (it) {
//                                getExpenseListOperation()
                            }
                        }
                    })
                }
            }
        }
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//
//        super.onActivityCreated(savedInstanceState)
//
//
//    }



    private fun loadTransactionHistory() {

    }
//
//    private fun getCategoriesOperation() {
//        //TODO : Make a Main activity with fragment, then replace the fragments
//        CategoriesFragment.newInstance().also {
//            val fragmentTransaction = supportFragmentManager.beginTransaction()
//            fragmentTransaction.show(it)
//        }
//    }
//
//    private fun getExpenseListOperation(){
//        // TODO : Add Expense Fragment
//    }
//
    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this.activity as MainActivity, R.layout.main_fragment)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }

    private fun addExpenseOperation(){

        EventBus.getDefault().post(EventMessage("Fire fragment", true))

    }
//
//    private fun onSuccess(){
////        Toast.makeText(this, "Successfully inserted", Toast.LENGTH_LONG).show()
//    }

}
