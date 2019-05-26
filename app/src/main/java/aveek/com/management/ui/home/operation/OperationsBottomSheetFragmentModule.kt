package aveek.com.management.ui.home.operation

import dagger.Module
import dagger.Provides
import android.arch.lifecycle.ViewModelProviders
import aveek.com.management.repository.DatabaseRepository


@Module
class  OperationsBottomSheetFragmentModule{

    @Provides
    fun viewModel (repository: DatabaseRepository) = ViewModelProviders.of(context).get(OperationsBottomSheetViewModel::class.java)

}