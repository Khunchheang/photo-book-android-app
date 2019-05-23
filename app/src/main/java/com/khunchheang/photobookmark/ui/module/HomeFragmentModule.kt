package com.khunchheang.photobookmark.ui.module

import com.khunchheang.photobookmark.ui.adapter.PhotoRecyclerAdapter
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractor
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkInteractor
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkPresenterImpl
import com.khunchheang.photobookmark.ui.mvp.homefragment.photolist.PhotoListInteractor
import com.khunchheang.photobookmark.ui.mvp.homefragment.photolist.PhotoListPresenter
import com.khunchheang.photobookmark.ui.mvp.homefragment.photolist.PhotoListPresenterImpl
import com.khunchheang.photobookmark.ui.scope.HomeFragmentScope
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule {

    @Provides
    @HomeFragmentScope
    fun providePhotoListPre(
        photoListInter: PhotoListInteractor,
        getBookmarkInter: GetBookmarkInteractor
    ): PhotoListPresenter {
        return PhotoListPresenterImpl(photoListInter, getBookmarkInter)
    }

    @Provides
    @HomeFragmentScope
    fun providePhotoAdapter() = PhotoRecyclerAdapter()

    @Provides
    @HomeFragmentScope
    fun provideAddBookmarkPre(addBookmarkInter: AddBookmarkInteractor): AddBookmarkPresenter {
        return AddBookmarkPresenterImpl(addBookmarkInter)
    }

}