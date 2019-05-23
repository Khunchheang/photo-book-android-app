package com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark

import com.khunchheang.photobook.ui.base.basemvp.BaseLocalPresenter
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.BookmarkFragmentView

interface GetBookmarkPresenter : BaseLocalPresenter<BookmarkFragmentView> {

    fun getPhotoBookmark()

}