package com.khunchheang.photobook.ui.mvp.homefragment.addbookmark

import com.khunchheang.photobook.data.local.BookmarkDao
import com.khunchheang.photobook.data.local.BookmarkRoomModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class AddBookmarkInteractorImpl(private val bookmarkDao: BookmarkDao) : AddBookmarkInteractor {

    private var insertDisposable: Disposable? = null
    private var deleteDisposable: Disposable? = null

    override fun onAddPhotoBookmark(bookmarkRoomModel: BookmarkRoomModel, completion: () -> Unit) {
        insertDisposable = Completable.fromAction { bookmarkDao.insert(bookmarkRoomModel) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                completion()
                insertDisposable?.dispose()
            }, {
                completion()
                insertDisposable?.dispose()
            })
    }

    override fun onRemovePhotoFromBookmark(photoId: Long, completion: () -> Unit) {
        deleteDisposable = Completable.fromAction { bookmarkDao.removeFromBookmark(photoId) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                completion()
                deleteDisposable?.dispose()
            }, {
                completion()
                deleteDisposable?.dispose()
            })
    }
}