package aveek.com.management

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import aveek.com.management.di.AppInjector
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BaseApp : MultiDexApplication() , HasActivityInjector{

    @Inject
    lateinit var dispatchingActivityInjector : DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        baseApp = this
        AppInjector.init(this)
        Stetho.initializeWithDefaults(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> {
        return  dispatchingActivityInjector
    }

    companion object {
        lateinit var baseApp: BaseApp
    }
}