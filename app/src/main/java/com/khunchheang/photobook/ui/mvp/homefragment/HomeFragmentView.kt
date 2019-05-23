package com.khunchheang.photobook.ui.mvp.homefragment

import com.khunchheang.photobook.data.PhotoItemResponse
import com.khunchheang.photobook.ui.base.basemvp.BaseMvpView
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkView

interface HomeFragmentView : BaseMvpView, AddBookmarkView {

    fun onPhotoListResponse(data: List<PhotoItemResponse>)

}