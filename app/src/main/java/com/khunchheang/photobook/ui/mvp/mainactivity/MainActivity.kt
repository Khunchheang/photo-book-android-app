package com.khunchheang.photobook.ui.mvp.mainactivity

import android.os.Bundle
import com.khunchheang.photobook.R
import com.khunchheang.photobook.ui.adapter.BottomNavigationPagerAdapter
import com.khunchheang.photobook.ui.mvp.aboutfragment.AboutFragment
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.BookmarkFragment
import com.khunchheang.photobook.ui.mvp.homefragment.HomeFragment
import com.odic.skybooking.ui.base.activity.BaseSupportToolbarActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject

class MainActivity : BaseSupportToolbarActivity() {

    @Inject
    lateinit var bottomNavAdapter: BottomNavigationPagerAdapter
    @Inject
    lateinit var homeFragment: HomeFragment
    @Inject
    lateinit var bookmarkFragment: BookmarkFragment
    @Inject
    lateinit var aboutFragment: AboutFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        title = getString(R.string.title_home)

        initBottomNav()
    }

    override fun onBackPressed() {
        if (viewpager.currentItem != HOME_FRAGMENT_INDEX) {
            viewpager.currentItem = HOME_FRAGMENT_INDEX
            nav_view.selectedItemId = R.id.nav_home
        } else {
            super.onBackPressed()
        }
    }

    private fun initBottomNav() {
        bottomNavAdapter.addFragments(homeFragment)
        bottomNavAdapter.addFragments(bookmarkFragment)
        bottomNavAdapter.addFragments(aboutFragment)

        viewpager.offscreenPageLimit = bottomNavAdapter.count
        viewpager.adapter = bottomNavAdapter

        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    title = getString(R.string.title_home)
                    viewpager.currentItem = HOME_FRAGMENT_INDEX
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_bookmark -> {
                    title = getString(R.string.title_bookmark)
                    viewpager.currentItem = BOOKMARK_FRAGMENT_INDEX
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_about -> {
                    title = getString(R.string.title_about)
                    viewpager.currentItem = ABOUT_FRAGMENT_INDEX
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }

        nav_view.setOnNavigationItemReselectedListener {
            if (it.itemId == R.id.nav_home) {
                homeFragment.view?.recycler_photo?.smoothScrollToPosition(0)
            }
        }
    }

    companion object {
        const val HOME_FRAGMENT_INDEX = 0
        const val BOOKMARK_FRAGMENT_INDEX = 1
        const val ABOUT_FRAGMENT_INDEX = 2
    }
}
