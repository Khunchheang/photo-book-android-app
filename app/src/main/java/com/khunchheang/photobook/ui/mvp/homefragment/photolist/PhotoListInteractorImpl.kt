package com.khunchheang.photobook.ui.mvp.homefragment.photolist

import com.khunchheang.photobook.data.PhotoItemResponse
import com.khunchheang.photobook.restclient.PicSumRestApi
import com.khunchheang.photobook.ui.base.basemvp.baseinteractorimpl.BaseInteractorImpl
import com.khunchheang.photobook.ui.base.basemvp.response.ResponseModel

class PhotoListInteractorImpl(private val picSumApi: PicSumRestApi) : BaseInteractorImpl<List<PhotoItemResponse>>(),
    PhotoListInteractor {

    override fun onGetPhotoList(page: Int, limit: Int, completion: (response: ResponseModel) -> Unit) {
        this.completion = completion

        call = picSumApi.getPhotoList(page, limit)
        call!!.enqueue(this)
    }
}