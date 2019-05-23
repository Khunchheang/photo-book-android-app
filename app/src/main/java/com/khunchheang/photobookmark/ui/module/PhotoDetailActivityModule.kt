package com.khunchheang.photobookmark.ui.module

import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkInteractor
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkPresenterImpl
import com.khunchheang.photobookmark.ui.scope.PhotoDetailActivityScope
import dagger.Module
import dagger.Provides

@Module
class PhotoDetailActivityModule {

    @Provides
    @PhotoDetailActivityScope
    fun provideAddBookmarkPre(addBookmarkInter: AddBookmarkInteractor): AddBookmarkPresenter {
        return AddBookmarkPresenterImpl(addBookmarkInter)
    }

}