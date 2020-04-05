package uae.weather.myapplication

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import uae.weather.myapplication.di.AppInjector.init
import javax.inject.Inject

class MyApplication :Application() , HasActivityInjector {

    @JvmField
    @Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector!!
    }
    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}