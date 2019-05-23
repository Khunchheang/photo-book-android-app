package com.khunchheang.photobook.di.module

import com.khunchheang.photobook.data.local.AppDatabase
import com.khunchheang.photobook.restclient.PicSumRestApi
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractor
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractorImpl
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkInteractor
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkInteractorImpl
import com.khunchheang.photobook.ui.mvp.homefragment.photolist.PhotoListInteractor
import com.khunchheang.photobook.ui.mvp.homefragment.photolist.PhotoListInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppInteractorModule {

    @Provides
    @Singleton
    fun providePhotoListInter(picSumApi: PicSumRestApi): PhotoListInteractor {
        return PhotoListInteractorImpl(picSumApi)
    }

    @Provides
    @Singleton
    fun provideAddBookmarkInter(appDatabase: AppDatabase): AddBookmarkInteractor {
        return AddBookmarkInteractorImpl(appDatabase.bookmarkDao())
    }

    @Provides
    @Singleton
    fun provideGetBookmarkInter(appDatabase: AppDatabase): GetBookmarkInteractor {
        return GetBookmarkInteractorImpl(appDatabase.bookmarkDao())
    }
}