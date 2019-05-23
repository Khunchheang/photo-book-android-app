package com.khunchheang.photobook.ui.mvp.bookmarkfragment

import com.khunchheang.photobook.data.local.BookmarkRoomModel
import com.khunchheang.photobook.ui.base.basemvp.BaseMvpView
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkView

interface BookmarkFragmentView : BaseMvpView, AddBookmarkView {

    fun onBookmarkResponse(data: ArrayList<BookmarkRoomModel>)

}