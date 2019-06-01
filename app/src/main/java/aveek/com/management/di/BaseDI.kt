package aveek.com.management.di

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import aveek.com.management.BaseApp
import aveek.com.management.ui.db.AppDatabase
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.ui.home.operation.OperationsBottomSheetFragment
import aveek.com.management.ui.transactions.TransactionActivity
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import android.arch.persistence.room.Room
import aveek.com.management.ui.db.dao.TransactionDAO
import aveek.com.management.ui.home.MainActivityViewModel
import aveek.com.management.ui.home.operation.OperationsBottomSheetViewModel
import aveek.com.management.ui.transactions.TransactionVM
import aveek.com.management.viewModel.ExpenseViewModelFactory
import dagger.*
import dagger.multibindings.IntoMap


@Singleton
@Component ( modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    LocalDependencyBuilder::class])
interface AppComponent : AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
    override fun inject(app : BaseApp)

}

// This class is responsible for all of the dependencies like retrofit, db, sharedPrefs etc

@Module (includes = [ViewModelModule::class])
internal class AppModule{
    @Provides
    @Singleton
    fun provideContext (application: BaseApp) : Context{
        return application
    }

    @Provides
    @Singleton
    fun provideDatabase (application: BaseApp) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "myDB").build()
    }

    @Provides
    @Singleton
    fun provideDao(database: AppDatabase): TransactionDAO= database.transactionDao()

}

@Module
internal abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionVM::class)
    abstract fun bindTransactionViewModel(transactionVM: TransactionVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OperationsBottomSheetViewModel::class)
    abstract fun bindOperationsBottomSheetViewModel(operationsBottomSheetViewModel: OperationsBottomSheetViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ExpenseViewModelFactory): ViewModelProvider.Factory
}

@Module
internal abstract class LocalDependencyBuilder{

//    @ContributesAndroidInjector(modules = [MainActivityModule::class, OperationsBottomSheetFragmentProvider::class])
    @ContributesAndroidInjector(modules = [OperationsBottomSheetFragmentProvider::class])
    abstract fun bindMainActivity() : MainActivity

//    @ContributesAndroidInjector(modules = [TransactionActivityModule::class])
    @ContributesAndroidInjector
    abstract fun bindTransactionActivity() : TransactionActivity

}

@Module
internal abstract class OperationsBottomSheetFragmentProvider{
    @ContributesAndroidInjector
    abstract fun bindOperationsBottomSheetFragment() : OperationsBottomSheetFragment
}



