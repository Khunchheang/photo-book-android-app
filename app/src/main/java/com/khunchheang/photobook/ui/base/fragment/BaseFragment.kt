package com.khunchheang.photobook.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khunchheang.photobook.ui.base.activity.BaseBasicActivity
import com.khunchheang.photobook.ui.dialog.PositiveNegativeDialog
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {
    private var mSavedInstanceState: Bundle? = null
    private var mHasInflated = false
    private var baseActivity: BaseBasicActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseBasicActivity) baseActivity = context
    }

    @get:LayoutRes
    protected abstract val layoutResource: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSavedInstanceState = savedInstanceState
        if (userVisibleHint && !mHasInflated) {
            onCreateViewInflated(view, mSavedInstanceState)
            afterViewStubInflated()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && view != null && !mHasInflated) {
            onCreateViewInflated(view!!, mSavedInstanceState)
            afterViewStubInflated()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mHasInflated = false
    }

    protected abstract fun onCreateViewInflated(inflatedView: View, savedInstanceState: Bundle?)

    @CallSuper
    protected fun afterViewStubInflated() {
        mHasInflated = true
    }

    internal fun showToast(msg: String) {
        baseActivity?.showToast(msg)
    }

    internal fun showToast(msg: Int) {
        baseActivity?.showToast(msg)
    }

    internal fun showDialogMessage(msg: String): PositiveNegativeDialog.Builder {
        return baseActivity?.showDialogMessage(msg)!!
    }
}
