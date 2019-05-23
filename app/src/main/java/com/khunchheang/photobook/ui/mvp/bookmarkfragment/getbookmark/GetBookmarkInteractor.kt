package com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark

import com.khunchheang.photobook.ui.base.basemvp.response.ResponseModel

interface GetBookmarkInteractor {

    fun onGetPhotoBookmark(completion: (response: ResponseModel) -> Unit)

    fun disposeGetBookmark()

}