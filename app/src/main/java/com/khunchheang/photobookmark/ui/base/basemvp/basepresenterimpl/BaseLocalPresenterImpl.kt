package com.khunchheang.photobookmark.ui.base.basemvp.basepresenterimpl

import com.khunchheang.photobookmark.ui.base.basemvp.BaseLocalPresenter

abstract class BaseLocalPresenterImpl<V> : BaseLocalPresenter<V> {

    internal var view: V? = null

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

}