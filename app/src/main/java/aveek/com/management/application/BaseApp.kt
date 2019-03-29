package aveek.com.management.application

import android.app.Activity
import androidx.multidex.MultiDexApplication
import aveek.com.management.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BaseApp : MultiDexApplication() , HasActivityInjector{

    @Inject
    lateinit var dispatchingActivityInjector : DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> {
        return  dispatchingActivityInjector
    }
}