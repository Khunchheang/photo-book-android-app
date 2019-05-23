package com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.getbookmark

import com.khunchheang.photobookmark.ui.base.basemvp.response.ResponseModel

interface GetBookmarkInteractor {

    fun onGetPhotoBookmark(completion: (response: ResponseModel) -> Unit)

    fun disposeGetBookmark()

}