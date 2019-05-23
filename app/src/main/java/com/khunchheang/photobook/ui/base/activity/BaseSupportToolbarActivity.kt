package com.odic.skybooking.ui.base.activity

import android.view.MenuItem
import com.khunchheang.photobook.ui.base.activity.BaseBasicActivity

abstract class BaseSupportToolbarActivity : BaseBasicActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    internal fun setSupportToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }
}