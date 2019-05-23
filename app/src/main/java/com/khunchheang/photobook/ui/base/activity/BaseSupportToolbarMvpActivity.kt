package com.khunchheang.photobook.ui.base.activity

import android.view.MenuItem
import com.khunchheang.photobook.ui.base.basemvp.BaseMvpView

abstract class BaseSupportToolbarMvpActivity : BaseMvpView, BaseBasicMvpActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
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