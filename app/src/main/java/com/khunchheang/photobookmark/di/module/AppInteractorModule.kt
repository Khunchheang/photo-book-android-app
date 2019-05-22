package com.khunchheang.photobookmark.di.module

import com.khunchheang.photobookmark.restclient.PicSumRestApi
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

}