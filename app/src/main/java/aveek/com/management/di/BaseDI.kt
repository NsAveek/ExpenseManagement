package aveek.com.management.di

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import aveek.com.management.BaseActivity
import aveek.com.management.BaseApp
import aveek.com.management.db.AppDatabase
import aveek.com.management.db.dao.TransactionDAO
import aveek.com.management.db.repository.DatabaseRepository
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.ui.home.MainActivityModule
import aveek.com.management.ui.home.MainActivityViewModel
import aveek.com.management.ui.home.operation.OperationsBottomSheetFragment
import aveek.com.management.ui.home.operation.OperationsBottomSheetViewModel
import aveek.com.management.ui.transactions.TransactionActivity
import aveek.com.management.ui.transactions.TransactionActivityModule
import aveek.com.management.ui.transactions.TransactionVM
import aveek.com.management.viewModel.ViewModelFactory
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import javax.inject.Singleton


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

//    fun getRepository(): DatabaseRepository
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
    fun provideDao(database: AppDatabase): TransactionDAO = database.transactionDao()

    @Provides
    @Singleton
    fun provideRepo(database: AppDatabase): DatabaseRepository = DatabaseRepository(database)


//    @Provides
//    @Singleton
//    fun provideViewModelFactory(database: AppDatabase): TransactionDAO= ViewModelFactory

}

@Module
internal abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(OperationsBottomSheetViewModel::class)
    abstract fun bindOperationsBottomSheetViewModel (operationsBottomSheetViewModel: OperationsBottomSheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel (mainActivityViewModel: MainActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TransactionVM::class)
    abstract fun bindTransactionViewModel (transactionVM: TransactionVM): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@Module
internal abstract class LocalDependencyBuilder{


    @ContributesAndroidInjector(modules = [MainActivityModule::class, OperationsBottomSheetFragmentProvider::class])
    abstract fun bindMainActivity() : MainActivity

    @ContributesAndroidInjector(modules = [TransactionActivityModule::class])
    abstract fun bindTransactionActivity() : TransactionActivity

}

@Module
internal abstract class OperationsBottomSheetFragmentProvider{
    @ContributesAndroidInjector
    abstract fun bindOperationsBottomSheetFragment() : OperationsBottomSheetFragment
}



