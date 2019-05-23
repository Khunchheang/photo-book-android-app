package com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark

interface AddBookmarkView {

    fun onAddBookmarkSuccess(pos: Int)

    fun onRemoveBookmarkSuccess(pos: Int)

    fun onPhotoIdError(msg: Int)

}