package com.khunchheang.photobookmark.di.builder

import com.khunchheang.photobookmark.ui.module.AboutFragmentModule
import com.khunchheang.photobookmark.ui.module.BookmarkFragmentModule
import com.khunchheang.photobookmark.ui.module.HomeFragmentModule
import com.khunchheang.photobookmark.ui.mvp.aboutfragment.AboutFragment
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.BookmarkFragment
import com.khunchheang.photobookmark.ui.mvp.homefragment.HomeFragment
import com.khunchheang.photobookmark.ui.scope.AboutFragmentScope
import com.khunchheang.photobookmark.ui.scope.BookmarkFragmentScope
import com.khunchheang.photobookmark.ui.scope.HomeFragmentScope
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