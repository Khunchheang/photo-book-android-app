package com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark

import com.khunchheang.photobookmark.data.local.BookmarkRoomModel

interface AddBookmarkInteractor {

    fun onAddPhotoBookmark(bookmarkRoomModel: BookmarkRoomModel, completion: () -> Unit)

    fun onRemovePhotoFromBookmark(photoId: Long, completion: () -> Unit)

}