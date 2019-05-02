package aveek.com.management.ui.home

import android.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aveek.com.management.R

import aveek.com.myapplication.R

class OperationsBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun getOperationsBottomSheetFragment() = OperationsBottomSheetFragment()
    }

    private lateinit var viewModel: OperationsBottomSheetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.operations_bottom_sheet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OperationsBottomSheetViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
