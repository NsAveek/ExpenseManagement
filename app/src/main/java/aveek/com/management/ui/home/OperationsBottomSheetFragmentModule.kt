package aveek.com.management.ui.home

import dagger.Module
import dagger.Provides
import android.arch.lifecycle.ViewModelProviders


@Module
class  OperationsBottomSheetFragmentModule{
    @Provides
    fun viewModel (context : OperationsBottomSheetFragment) = ViewModelProviders.of(context).get(OperationsBottomSheetViewModel::class.java)

}