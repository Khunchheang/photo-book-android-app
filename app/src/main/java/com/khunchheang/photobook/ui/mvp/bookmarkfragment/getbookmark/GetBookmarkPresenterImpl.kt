package com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark

import com.khunchheang.photobook.data.local.BookmarkRoomModel
import com.khunchheang.photobook.ui.base.basemvp.basepresenterimpl.BaseLocalPresenterImpl
import com.khunchheang.photobook.ui.base.basemvp.response.ErrorResponseIntModel
import com.khunchheang.photobook.ui.base.basemvp.response.SuccessResponseModel
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.BookmarkFragmentView

class GetBookmarkPresenterImpl(private val getBookmarkInter: GetBookmarkInteractor) :
    BaseLocalPresenterImpl<BookmarkFragmentView>(), GetBookmarkPresenter {

    @Suppress("UNCHECKED_CAST")
    override fun getPhotoBookmark() {
        getBookmarkInter.onGetPhotoBookmark {
            if (it is SuccessResponseModel<*>) {
                val data = it.data as ArrayList<BookmarkRoomModel>
                view?.onBookmarkResponse(data)
            } else if (it is ErrorResponseIntModel) {
                view?.showError(it.msg)
            }
        }
    }

    override fun detach() {
        getBookmarkInter.disposeGetBookmark()
        super.detach()
    }
}