package com.khunchheang.photobookmark.ui.mvp.homefragment

import com.khunchheang.photobookmark.data.PhotoItemResponse
import com.khunchheang.photobookmark.ui.base.basemvp.BaseMvpView
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkView

interface HomeFragmentView : BaseMvpView, AddBookmarkView {

    fun onPhotoListResponse(data: List<PhotoItemResponse>)

}