package aveek.com.management.di

import android.app.Application
import android.content.Context
import aveek.com.management.BaseApp
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.ui.home.MainActivityModule
import aveek.com.management.ui.home.OperationsBottomSheetFragment
import aveek.com.management.ui.home.OperationsBottomSheetFragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
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
}

// This class is responsible for all of the dependencies like retrofit, db, sharedPrefs etc

@Module
internal class AppModule{
    @Provides
    @Singleton
    fun provideContext (application: BaseApp) : Context{
        return application
    }
}

@Module
internal abstract class LocalDependencyBuilder{

    @ContributesAndroidInjector(modules = [MainActivityModule::class, OperationsBottomSheetFragmentProvider::class])
    abstract fun bindMainActivity() : MainActivity

//    @ContributesAndroidInjector(modules = arrayOf(DetailActivityModule::class, DetailFragmentProvider::class))
//    internal abstract fun bindDetailActivity(): DetailActivity

//    @ContributesAndroidInjector(modules = [OperationsBottomSheetFragmentModule::class])
//    abstract fun bindOperationsBottomSheetFragment() : OperationsBottomSheetFragment
}

@Module
internal abstract class OperationsBottomSheetFragmentProvider{
    @ContributesAndroidInjector(modules = [OperationsBottomSheetFragmentModule::class])
    abstract fun bindOperationsBottomSheetFragment() : OperationsBottomSheetFragment
}



