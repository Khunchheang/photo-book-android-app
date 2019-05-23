package com.khunchheang.photobook.di.builder

import com.khunchheang.photobook.ui.module.AboutFragmentModule
import com.khunchheang.photobook.ui.module.BookmarkFragmentModule
import com.khunchheang.photobook.ui.module.HomeFragmentModule
import com.khunchheang.photobook.ui.mvp.aboutfragment.AboutFragment
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.BookmarkFragment
import com.khunchheang.photobook.ui.mvp.homefragment.HomeFragment
import com.khunchheang.photobook.ui.scope.AboutFragmentScope
import com.khunchheang.photobook.ui.scope.BookmarkFragmentScope
import com.khunchheang.photobook.ui.scope.HomeFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @HomeFragmentScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun bindHomeFragment(): HomeFragment

    @BookmarkFragmentScope
    @ContributesAndroidInjector(modules = [BookmarkFragmentModule::class])
    abstract fun bindBookmarkFragment(): BookmarkFragment

    @AboutFragmentScope
    @ContributesAndroidInjector(modules = [AboutFragmentModule::class])
    abstract fun bindAboutFragment(): AboutFragment
}