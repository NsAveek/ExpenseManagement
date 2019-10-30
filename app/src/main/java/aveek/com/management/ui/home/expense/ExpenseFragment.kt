package aveek.com.management.ui.home.expense

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import aveek.com.management.R
import aveek.com.management.databinding.CategoriesFragmentBinding
import aveek.com.management.databinding.ExpenseFragmentBinding
import aveek.com.management.di.Injectable
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.ui.home.categories.CategoriesViewModel

class ExpenseFragment : Fragment(), Injectable {

    companion object {
        fun newInstance()=ExpenseFragment()
    }

    private lateinit var viewModel: ExpenseViewModel
    private lateinit var binding : ExpenseFragmentBinding
    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel=ViewModelProviders.of(this).get(ExpenseViewModel::class.java)

        initBinding()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        return inflater.inflate(R.layout.expense_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this.activity as MainActivity, R.layout.expense_fragment)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }
}
