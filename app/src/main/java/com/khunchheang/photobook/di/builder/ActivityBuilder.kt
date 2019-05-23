package com.khunchheang.photobook.di.builder

import com.khunchheang.photobook.ui.module.MainActivityModule
import com.khunchheang.photobook.ui.module.PhotoDetailActivityModule
import com.khunchheang.photobook.ui.mvp.mainactivity.MainActivity
import com.khunchheang.photobook.ui.mvp.photodetail.PhotoDetailActivity
import com.khunchheang.photobook.ui.scope.MainActivityScope
import com.khunchheang.photobook.ui.scope.PhotoDetailActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @MainActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @PhotoDetailActivityScope
    @ContributesAndroidInjector(modules = [PhotoDetailActivityModule::class])
    abstract fun bindPhotoDetailActivity(): PhotoDetailActivity

}