package aveek.com.management.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aveek.com.management.R
import aveek.com.management.util.EnumEventState


class OperationsBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun getOperationsBottomSheetFragment() = OperationsBottomSheetFragment()
    }


    val dismissOrProceedEvent = MutableLiveData<Pair<EnumEventState,Any>>()

    private lateinit var viewModel: OperationsBottomSheetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.operations_bottom_sheet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OperationsBottomSheetViewModel::class.java)
        viewModel.add().observe(this, Observer {
            it?.let {
                if (it){
                    dismissOrProceedEvent.value = Pair(EnumEventState.PROCEED,"nothing new")
                }
            }
        })
        viewModel.dismiss().observe(this, Observer {
            it?.let {
                if (it){
                    dismissOrProceedEvent.value = Pair(EnumEventState.DISMISS,"nothing new")
                }
            }
        })

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        AndroidSupportInjection.inject(this);
    }

}
