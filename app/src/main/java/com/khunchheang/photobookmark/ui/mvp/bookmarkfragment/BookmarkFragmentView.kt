package com.khunchheang.photobookmark.ui.mvp.bookmarkfragment

import com.khunchheang.photobookmark.data.local.BookmarkRoomModel
import com.khunchheang.photobookmark.ui.base.basemvp.BaseMvpView
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkView

interface BookmarkFragmentView : BaseMvpView, AddBookmarkView {

    fun onBookmarkResponse(data: ArrayList<BookmarkRoomModel>)

}