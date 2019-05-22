package com.khunchheang.photobookmark.ui.base.basemvp.basepresenterimpl

import com.khunchheang.photobookmark.ui.base.basemvp.BaseInteractor
import com.khunchheang.photobookmark.ui.base.basemvp.BaseMvpView
import com.khunchheang.photobookmark.ui.base.basemvp.BasePresenter
import com.khunchheang.photobookmark.ui.base.basemvp.response.*

abstract class BasePresenterImpl<T, V : BaseMvpView> : BasePresenter<V> {

    var view: V? = null

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        view = null
        getInter().onCancelLoading()
        getInter().clearReference()
    }

    override fun cancelLoading() {
        view?.hideLoading()
        getInter().onCancelLoading()
    }

    override fun retry() {
        view?.showLoading()
        getInter().onRetry {
            onResponseData(it)
        }
    }

    override fun onResponseData(response: ResponseModel) {
        view?.hideLoading()

        when (response) {
            is SuccessResponseModel<*> -> {
                @Suppress("UNCHECKED_CAST")
                onSuccess(response.data as T)
            }
            is ExceptionResponseModel -> {
                view?.showException(response.msg)
            }
            is ErrorResponseModel -> {
                view?.showError(response.msg)
            }
            is ErrorResponseIntModel -> {
                view?.showError(response.msg)
            }
            is ServerDownResponseModel -> {
                view?.onServerDown(response.msg)
            }
            is UnauthorizedResponseModel -> {
                view?.onUnauthorized(response.msg)
            }
        }
    }

    protected abstract fun onSuccess(data: T)

    protected abstract fun getInter(): BaseInteractor<T>
}