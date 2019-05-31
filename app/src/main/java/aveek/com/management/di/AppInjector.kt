package aveek.com.management.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import aveek.com.management.BaseApp
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {
    fun init(app : BaseApp){
        DaggerAppComponent.builder()
                .application(app)
                .build()
                .inject(app)
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: Activity, savedInstaceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityResumed(p0: Activity?) {
            }

            override fun onActivityPaused(p0: Activity?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityDestroyed(p0: Activity?) {
            }

        })
    }
    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentCreated(
                                        fm: FragmentManager,
                                        f: Fragment,
                                        savedInstanceState: Bundle?
                                ) {
                                    if (f is Injectable) {
                                        AndroidSupportInjection.inject(f)
                                    }
                                }
                            }, true
                    )
        }
    }
}