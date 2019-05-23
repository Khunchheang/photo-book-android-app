package com.khunchheang.photobook.ui.base.basemvp

import com.khunchheang.photobook.ui.base.basemvp.response.ResponseModel

interface BasePresenter<V : BaseMvpView> {

    fun attach(view: V)

    fun detach()

    fun cancelLoading()

    fun retry()

    fun onResponseData(response: ResponseModel)

}