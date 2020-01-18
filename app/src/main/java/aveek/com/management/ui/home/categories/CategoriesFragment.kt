package aveek.com.management.ui.home.categories

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
import aveek.com.management.databinding.MainFragmentBinding
import aveek.com.management.di.Injectable
import aveek.com.management.ui.home.MainActivity

class CategoriesFragment : Fragment(), Injectable {

    private lateinit var binding : CategoriesFragmentBinding
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var mLifecycleRegistry: LifecycleRegistry

    companion object {
        fun newInstance() = CategoriesFragment()
        val name = "Categories Fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)

        initBinding()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        return inflater.inflate(R.layout.categories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this.activity as MainActivity, R.layout.categories_fragment)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }
}
