package com.khunchheang.photobookmark.ui.mvp.homefragment

import com.khunchheang.photobookmark.data.PhotoItemResponse
import com.khunchheang.photobookmark.ui.base.basemvp.BaseMvpView

interface HomeFragmentView : BaseMvpView {

    fun onPhotoListResponse(data: List<PhotoItemResponse>)

}