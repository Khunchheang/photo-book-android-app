package com.khunchheang.photobook.ui.base.basemvp

import com.khunchheang.photobook.ui.base.basemvp.response.ResponseModel

interface BaseInteractor<T> {

    fun onCancelLoading()

    fun onRetry(completion: (responseModel: ResponseModel) -> Unit)

    fun clearReference()

}