package com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.getbookmark

import com.khunchheang.photobookmark.ui.base.basemvp.BaseLocalPresenter
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.BookmarkFragmentView

interface GetBookmarkPresenter : BaseLocalPresenter<BookmarkFragmentView> {

    fun getPhotoBookmark()

}