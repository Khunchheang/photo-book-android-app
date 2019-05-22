package com.khunchheang.photobookmark.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.khunchheang.photobookmark.ui.base.adapter.SmartFragmentStatePagerAdapter
import java.util.ArrayList

class BottomNavigationPagerAdapter(fragmentManager: FragmentManager) : SmartFragmentStatePagerAdapter(fragmentManager) {
    private val fragments = ArrayList<Fragment>()

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    // Our custom method that populates this Adapter with Fragments
    fun addFragments(fragment: Fragment) {
        fragments.add(fragment)
    }
}