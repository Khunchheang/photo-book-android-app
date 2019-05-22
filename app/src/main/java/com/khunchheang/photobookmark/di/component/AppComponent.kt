package com.odic.skybooking.di.component

import android.app.Application
import com.khunchheang.photobookmark.app.AppApplication
import com.khunchheang.photobookmark.di.module.AppInteractorModule
import com.khunchheang.photobookmark.di.builder.ActivityBuilder
import com.khunchheang.photobookmark.di.builder.FragmentBuilder
import com.khunchheang.photobookmark.di.module.AppModule
import com.khunchheang.photobookmark.di.module.RestClientModule
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