package com.khunchheang.photobook.ui.mvp.homefragment.addbookmark

import com.khunchheang.photobook.R
import com.khunchheang.photobook.data.local.BookmarkRoomModel
import com.khunchheang.photobook.ui.base.basemvp.basepresenterimpl.BaseLocalPresenterImpl

class AddBookmarkPresenterImpl(private val addBookmarkInter: AddBookmarkInteractor) :
    BaseLocalPresenterImpl<AddBookmarkView>(), AddBookmarkPresenter {

    override fun addPhotoBookmark(position: Int, photoId: String?, downloadUrl: String?) {
        if (photoId == null || downloadUrl == null) {
            view?.onPhotoIdError(R.string.sorry_this_photo_cannot_load)
            return
        }

        val bookmarkRoomModel = BookmarkRoomModel().apply {
            this.photoId = photoId.toLong()
            this.listUrl = "https://picsum.photos/id/$photoId/500/300"
            this.downloadUrl = downloadUrl
        }

        addBookmarkInter.onAddPhotoBookmark(bookmarkRoomModel) {
            view?.onAddBookmarkSuccess(position)
        }
    }

    override fun removePhotoBookmark(position: Int, photoId: String?) {
        if (photoId == null) {
            view?.onPhotoIdError(R.string.sorry_this_photo_cannot_load)
            return
        }

        addBookmarkInter.onRemovePhotoFromBookmark(photoId.toLong()) {
            view?.onRemoveBookmarkSuccess(position)
        }
    }
}