package aveek.com.management.ui.home.operation

import android.arch.lifecycle.*
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aveek.com.management.R
import aveek.com.management.databinding.OperationsBottomSheetFragmentBinding
import aveek.com.management.util.EnumEventState
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class OperationsBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun getOperationsBottomSheetFragment() =OperationsBottomSheetFragment()
    }

    val dismissOrProceedEvent = MutableLiveData<Pair<EnumEventState,Any>>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: OperationsBottomSheetViewModel

    private lateinit var binding: OperationsBottomSheetFragmentBinding

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(OperationsBottomSheetViewModel::class.java)
        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.operations_bottom_sheet_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)

        binding.viewmodel?.let {localViewModel ->
            with(localViewModel){
//                getTransaction().observe(this@OperationsBottomSheetFragment, Observer {
//                    it?.let { transaction ->
//                            dismissOrProceedEvent.value = Pair(EnumEventState.PROCEED,transaction)
//                            dismiss()
//                    }
//                })
                addTransactionData().observe(this@OperationsBottomSheetFragment, Observer {
                    it?.let { transactionStatus ->
                        dismissOrProceedEvent.value = Pair(EnumEventState.PROCEED,transactionStatus)
                        dismiss()
                    }
                })
                getDismissCommand().observe(this@OperationsBottomSheetFragment, Observer {
                    it?.let {
                        if (it){
                            dismissOrProceedEvent.value = Pair(EnumEventState.DISMISS,"nothing new")
                            dismiss()
                        }
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
    }

    /*
    * Lifecycle aware handling methods
    * */

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }


    override fun getView(): View? {
        return super.getView()
    }

    override fun onPause() {
        super.onPause()
    }
}
