package com.khunchheang.photobookmark.ui.mvp.homefragment.photolist

import com.khunchheang.photobookmark.app.AppConstants
import com.khunchheang.photobookmark.ui.base.basemvp.BasePresenter
import com.khunchheang.photobookmark.ui.mvp.homefragment.HomeFragmentView

interface PhotoListPresenter : BasePresenter<HomeFragmentView> {

    fun getPhotoList(page: Int = 1, limit: Int = AppConstants.QUERY_LIMIT)

}