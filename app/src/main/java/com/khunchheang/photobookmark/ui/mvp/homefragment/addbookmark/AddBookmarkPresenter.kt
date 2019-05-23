package com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark

import com.khunchheang.photobookmark.ui.base.basemvp.BaseLocalPresenter

interface AddBookmarkPresenter : BaseLocalPresenter<AddBookmarkView> {

    fun addPhotoBookmark(position: Int = 0, photoId: String?, downloadUrl: String?)

    fun removePhotoBookmark(position: Int = 0, photoId: String?)
}