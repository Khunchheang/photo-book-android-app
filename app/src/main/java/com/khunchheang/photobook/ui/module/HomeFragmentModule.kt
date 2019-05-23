package com.khunchheang.photobook.ui.module

import com.khunchheang.photobook.ui.adapter.PhotoRecyclerAdapter
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractor
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkInteractor
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkPresenterImpl
import com.khunchheang.photobook.ui.mvp.homefragment.photolist.PhotoListInteractor
import com.khunchheang.photobook.ui.mvp.homefragment.photolist.PhotoListPresenter
import com.khunchheang.photobook.ui.mvp.homefragment.photolist.PhotoListPresenterImpl
import com.khunchheang.photobook.ui.scope.HomeFragmentScope
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