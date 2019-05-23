package com.khunchheang.photobookmark.di.module

import com.khunchheang.photobookmark.data.local.AppDatabase
import com.khunchheang.photobookmark.restclient.PicSumRestApi
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractor
import com.khunchheang.photobookmark.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkInteractorImpl
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkInteractor
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkInteractorImpl
import com.khunchheang.photobookmark.ui.mvp.homefragment.photolist.PhotoListInteractor
import com.khunchheang.photobookmark.ui.mvp.homefragment.photolist.PhotoListInteractorImpl
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