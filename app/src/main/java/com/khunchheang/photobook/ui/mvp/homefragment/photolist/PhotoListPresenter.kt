package com.khunchheang.photobook.ui.mvp.homefragment.photolist

import com.khunchheang.photobook.app.AppConstants
import com.khunchheang.photobook.ui.base.basemvp.BasePresenter
import com.khunchheang.photobook.ui.mvp.homefragment.HomeFragmentView

interface PhotoListPresenter : BasePresenter<HomeFragmentView> {

    fun getPhotoList(page: Int = 1, limit: Int = AppConstants.QUERY_LIMIT)

}