package com.khunchheang.photobookmark.ui.base.basemvp

interface BaseLocalPresenter<V> {

    fun attach(view: V)

    fun detach()

}