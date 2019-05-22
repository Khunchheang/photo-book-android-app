package com.khunchheang.photobookmark.ui.mvp.homefragment.photolist

import com.khunchheang.photobookmark.data.PhotoItemResponse
import com.khunchheang.photobookmark.ui.base.basemvp.BaseInteractor
import com.khunchheang.photobookmark.ui.base.basemvp.response.ResponseModel

interface PhotoListInteractor : BaseInteractor<List<PhotoItemResponse>> {

    fun onGetPhotoList(page: Int, limit: Int, completion: (response: ResponseModel) -> Unit)

}