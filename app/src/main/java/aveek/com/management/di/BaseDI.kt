package aveek.com.management.di

import android.app.Application
import android.content.Context
import aveek.com.management.application.BaseApp
import aveek.com.management.ui.home.MainActivity
import aveek.com.management.ui.home.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Singleton
@Component ( modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class])
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
internal abstract class ActivityBuilder{

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity() : MainActivity

//    @ContributesAndroidInjector(modules = arrayOf(DetailActivityModule::class, DetailFragmentProvider::class))
//    internal abstract fun bindDetailActivity(): DetailActivity
}

