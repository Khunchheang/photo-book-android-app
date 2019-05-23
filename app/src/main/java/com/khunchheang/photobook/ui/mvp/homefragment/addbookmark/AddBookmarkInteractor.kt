package com.khunchheang.photobook.ui.mvp.homefragment.addbookmark

import com.khunchheang.photobook.data.local.BookmarkRoomModel

interface AddBookmarkInteractor {

    fun onAddPhotoBookmark(bookmarkRoomModel: BookmarkRoomModel, completion: () -> Unit)

    fun onRemovePhotoFromBookmark(photoId: Long, completion: () -> Unit)

}