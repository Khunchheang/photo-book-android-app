package com.khunchheang.photobook.ui.base.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.khunchheang.photobook.ui.dialog.PositiveNegativeDialog
import dagger.android.AndroidInjection

abstract class BaseBasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        Log.d("ActivityLifecycle", String.format("onCreate (%s)", this.javaClass.simpleName))
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", String.format("onResume (%s)", this.javaClass.simpleName))
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityLifecycle", String.format("onPause (%s)", this.javaClass.simpleName))
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("ActivityLifecycle", String.format("onRestart (%s)", this.javaClass.simpleName))
    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifecycle", String.format("onStop (%s)", this.javaClass.simpleName))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("ActivityLifecycle", String.format("onActivityResult (%s)", this.javaClass.simpleName))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifecycle", String.format("onDestroy (%s)", this.javaClass.simpleName))
    }

    internal fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    internal fun showToast(msg: Int) {
        showToast(getString(msg))
    }

    internal fun showDialogMessage(msg: String): PositiveNegativeDialog.Builder {
        return PositiveNegativeDialog.Builder(supportFragmentManager).setMessage(msg)
    }

    open fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) view = View(applicationContext)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}