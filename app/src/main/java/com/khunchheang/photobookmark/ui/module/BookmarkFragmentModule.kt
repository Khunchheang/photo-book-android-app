package com.khunchheang.photobookmark.ui.module

import com.khunchheang.photobookmark.ui.adapter.BookmarkRecyclerAdapter
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractor
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkPresenter
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkPresenterImpl
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkInteractor
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkPresenterImpl
import com.khunchheang.photobookmark.ui.scope.BookmarkFragmentScope
import com.khunchheang.photobookmark.ui.scope.HomeFragmentScope
import dagger.Module
import dagger.Provides

@Module
class BookmarkFragmentModule {

    @Provides
    @BookmarkFragmentScope
    fun provideBookmarkAdapter() = BookmarkRecyclerAdapter()

    @Provides
    @BookmarkFragmentScope
    fun provideAddBookmarkPre(addBookmarkInter: AddBookmarkInteractor): AddBookmarkPresenter {
        return AddBookmarkPresenterImpl(addBookmarkInter)
    }

    @Provides
    @BookmarkFragmentScope
    fun provideGetBookmarkPre(getBookmarkInter: GetBookmarkInteractor): GetBookmarkPresenter {
        return GetBookmarkPresenterImpl(getBookmarkInter)
    }
}