package com.odic.skybooking.di.component

import android.app.Application
import com.khunchheang.photobook.app.AppApplication
import com.khunchheang.photobook.di.module.AppInteractorModule
import com.khunchheang.photobook.di.builder.ActivityBuilder
import com.khunchheang.photobook.di.builder.FragmentBuilder
import com.khunchheang.photobook.di.module.AppModule
import com.khunchheang.photobook.di.module.RestClientModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RestClientModule::class,
        AppInteractorModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class
    ]
)
interface AppComponent {

    fun inject(app: AppApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}