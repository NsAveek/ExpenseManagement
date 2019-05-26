package aveek.com.management.di

import android.app.Application
import android.arch.persistence.room.Database
import android.content.Context
import aveek.com.management.BaseApp
import aveek.com.management.ui.db.AppDatabase
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.ui.home.MainActivityModule
import aveek.com.management.ui.home.operation.OperationsBottomSheetFragment
import aveek.com.management.ui.home.operation.OperationsBottomSheetFragmentModule
import aveek.com.management.ui.transactions.TransactionActivity
import aveek.com.management.ui.transactions.TransactionActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import android.arch.persistence.room.Room
import aveek.com.management.repository.DatabaseRepository
import aveek.com.management.ui.db.dao.TransactionDAO


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

    fun getRepository(): DatabaseRepository
}

// This class is responsible for all of the dependencies like retrofit, db, sharedPrefs etc

@Module
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
internal abstract class LocalDependencyBuilder{

    @ContributesAndroidInjector(modules = [MainActivityModule::class, OperationsBottomSheetFragmentProvider::class])
    abstract fun bindMainActivity() : MainActivity

    @ContributesAndroidInjector(modules = [TransactionActivityModule::class])
    abstract fun bindTransactionActivity() : TransactionActivity

}

@Module
internal abstract class OperationsBottomSheetFragmentProvider{
    @ContributesAndroidInjector(modules = [OperationsBottomSheetFragmentModule::class])
    abstract fun bindOperationsBottomSheetFragment() : OperationsBottomSheetFragment
}



