package aveek.com.management.ui.transactions

import android.arch.lifecycle.ViewModelProviders
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.ui.home.MainActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class TransactionActivityModule {

    @Provides
    fun viewModel (context : TransactionActivity) = ViewModelProviders.of(context).get(TransactionVM::class.java)
}