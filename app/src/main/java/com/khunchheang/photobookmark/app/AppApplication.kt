package com.khunchheang.photobookmark.app

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.odic.skybooking.di.component.AppComponent
import com.odic.skybooking.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class AppApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    internal lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    internal lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate() {
        super.onCreate()

        val component: AppComponent by lazy {
            DaggerAppComponent.builder()
                .application(this)
                .build()
        }
        component.inject(this)
    }
}