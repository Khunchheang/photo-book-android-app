package com.khunchheang.photobookmark.ui.module

import com.khunchheang.photobookmark.ui.adapter.BottomNavigationPagerAdapter
import com.khunchheang.photobookmark.ui.mvp.aboutfragment.AboutFragment
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.BookmarkFragment
import com.khunchheang.photobookmark.ui.mvp.homefragment.HomeFragment
import com.khunchheang.photobookmark.ui.mvp.mainactivity.MainActivity
import com.khunchheang.photobookmark.ui.scope.MainActivityScope
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