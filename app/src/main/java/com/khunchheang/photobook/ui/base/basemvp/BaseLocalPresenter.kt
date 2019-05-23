package com.khunchheang.photobook.ui.base.basemvp

interface BaseLocalPresenter<V> {

    fun attach(view: V)

    fun detach()

}