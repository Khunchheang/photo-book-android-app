package com.khunchheang.photobook.ui.mvp.homefragment.photolist

import com.khunchheang.photobook.data.PhotoItemResponse
import com.khunchheang.photobook.data.local.BookmarkRoomModel
import com.khunchheang.photobook.ui.base.basemvp.BaseInteractor
import com.khunchheang.photobook.ui.base.basemvp.basepresenterimpl.BasePresenterImpl
import com.khunchheang.photobook.ui.base.basemvp.response.SuccessResponseModel
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractor
import com.khunchheang.photobook.ui.mvp.homefragment.HomeFragmentView

class PhotoListPresenterImpl(
    private val photoListInter: PhotoListInteractor,
    private val getBookmarkInter: GetBookmarkInteractor
) :
    BasePresenterImpl<List<PhotoItemResponse>, HomeFragmentView>(), PhotoListPresenter {

    override fun onSuccess(data: List<PhotoItemResponse>) {
        view?.onPhotoListResponse(data)
    }

    override fun getInter(): BaseInteractor<List<PhotoItemResponse>> {
        return photoListInter
    }

    @Suppress("UNCHECKED_CAST")
    override fun getPhotoList(page: Int, limit: Int) {
        if (page == 1) view?.showLoading()

        getBookmarkInter.onGetPhotoBookmark { bookmarkResponse ->
            val bookmarkIdList = ArrayList<Long>()
            getBookmarkInter.disposeGetBookmark()
            if (bookmarkResponse is SuccessResponseModel<*>) {
                val bookmarkList = bookmarkResponse.data as ArrayList<BookmarkRoomModel>
                bookmarkList.forEach { itemBookmark ->
                    itemBookmark.photoId.let { bookmarkIdList.add(it!!) }
                }
            }

            photoListInter.onGetPhotoList(page, limit) { photoResponse ->
                if (photoResponse is SuccessResponseModel<*>) {
                    view?.hideLoading()
                    val photoListResponse = photoResponse.data as ArrayList<PhotoItemResponse>
                    photoListResponse.forEach { photoItem ->
                        photoItem.id.let {
                            if (bookmarkIdList.contains(it!!.toLong())) photoItem.isAddedBookmark = true
                        }
                    }
                    view?.onPhotoListResponse(photoListResponse)
                } else {
                    onResponseData(photoResponse)
                }
            }
        }
    }
}