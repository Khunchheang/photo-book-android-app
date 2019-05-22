package com.khunchheang.photobookmark.ui.mvp.homefragment.photolist

import com.khunchheang.photobookmark.data.PhotoItemResponse
import com.khunchheang.photobookmark.ui.base.basemvp.BaseInteractor
import com.khunchheang.photobookmark.ui.base.basemvp.basepresenterimpl.BasePresenterImpl
import com.khunchheang.photobookmark.ui.mvp.homefragment.HomeFragmentView

class PhotoListPresenterImpl(private val photoListInter: PhotoListInteractor) :
    BasePresenterImpl<List<PhotoItemResponse>, HomeFragmentView>(), PhotoListPresenter {

    override fun onSuccess(data: List<PhotoItemResponse>) {
        view?.onPhotoListResponse(data)
    }

    override fun getInter(): BaseInteractor<List<PhotoItemResponse>> {
        return photoListInter
    }

    override fun getPhotoList(page: Int, limit: Int) {
        if (page == 1) view?.showLoading()

        photoListInter.onGetPhotoList(page, limit) {
            onResponseData(it)
        }
    }
}