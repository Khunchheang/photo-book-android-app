package com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark

import com.khunchheang.photobook.R
import com.khunchheang.photobook.data.local.BookmarkDao
import com.khunchheang.photobook.ui.base.basemvp.response.ErrorResponseIntModel
import com.khunchheang.photobook.ui.base.basemvp.response.ResponseModel
import com.khunchheang.photobook.ui.base.basemvp.response.SuccessResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class GetBookmarkInteractorImpl(private val bookmarkDao: BookmarkDao) : GetBookmarkInteractor {

    private var getBookmarkDisposable: Disposable? = null

    override fun onGetPhotoBookmark(completion: (response: ResponseModel) -> Unit) {
        getBookmarkDisposable = bookmarkDao.loadAllBookmarkPhoto()
            .delay(50, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                completion(SuccessResponseModel(it))
            }, {
                it.localizedMessage
                completion(ErrorResponseIntModel(R.string.cannot_load_bookmark))
            })
    }

    override fun disposeGetBookmark() {
        getBookmarkDisposable?.dispose()
    }
}