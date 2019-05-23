package com.khunchheang.photobook.ui.module

import com.khunchheang.photobook.ui.adapter.BottomNavigationPagerAdapter
import com.khunchheang.photobook.ui.mvp.aboutfragment.AboutFragment
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.BookmarkFragment
import com.khunchheang.photobook.ui.mvp.homefragment.HomeFragment
import com.khunchheang.photobook.ui.mvp.mainactivity.MainActivity
import com.khunchheang.photobook.ui.scope.MainActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @MainActivityScope
    @Provides
    fun provideBottomNavAdapter(mainActivity: MainActivity) =
        BottomNavigationPagerAdapter(mainActivity.supportFragmentManager)

    @MainActivityScope
    @Provides
    fun provideHomeFragment() = HomeFragment()

    @MainActivityScope
    @Provides
    fun provideBookmarkFragment() = BookmarkFragment()

    @MainActivityScope
    @Provides
    fun provideAboutFragment() = AboutFragment()

}