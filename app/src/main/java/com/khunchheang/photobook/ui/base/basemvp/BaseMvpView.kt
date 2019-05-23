package com.khunchheang.photobook.ui.base.basemvp

interface BaseMvpView {

    fun showError(msg: String)

    fun showError(msg: Int)

    fun showException(msg: String)

    fun showLoading()

    fun hideLoading()

    fun onServerDown(msg: Int)

    fun onUnauthorized(msg: Int)
}