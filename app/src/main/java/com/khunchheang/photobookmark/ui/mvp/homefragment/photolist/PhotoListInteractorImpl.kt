package com.khunchheang.photobookmark.ui.mvp.homefragment.photolist

import com.khunchheang.photobookmark.data.PhotoItemResponse
import com.khunchheang.photobookmark.restclient.PicSumRestApi
import com.khunchheang.photobookmark.ui.base.basemvp.baseinteractorimpl.BaseInteractorImpl
import com.khunchheang.photobookmark.ui.base.basemvp.response.ResponseModel

class PhotoListInteractorImpl(private val picSumApi: PicSumRestApi) : BaseInteractorImpl<List<PhotoItemResponse>>(),
    PhotoListInteractor {

    override fun onGetPhotoList(page: Int, limit: Int, completion: (response: ResponseModel) -> Unit) {
        this.completion = completion

        call = picSumApi.getPhotoList(page, limit)
        call!!.enqueue(this)
    }
}