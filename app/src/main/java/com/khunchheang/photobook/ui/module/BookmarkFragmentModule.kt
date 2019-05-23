package com.khunchheang.photobook.ui.module

import com.khunchheang.photobook.ui.adapter.BookmarkRecyclerAdapter
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractor
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkPresenter
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkPresenterImpl
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkInteractor
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkPresenterImpl
import com.khunchheang.photobook.ui.scope.BookmarkFragmentScope
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