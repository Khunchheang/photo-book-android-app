package com.khunchheang.photobook.ui.mvp.homefragment.photolist

import com.khunchheang.photobook.data.PhotoItemResponse
import com.khunchheang.photobook.ui.base.basemvp.BaseInteractor
import com.khunchheang.photobook.ui.base.basemvp.response.ResponseModel

interface PhotoListInteractor : BaseInteractor<List<PhotoItemResponse>> {

    fun onGetPhotoList(page: Int, limit: Int, completion: (response: ResponseModel) -> Unit)

}