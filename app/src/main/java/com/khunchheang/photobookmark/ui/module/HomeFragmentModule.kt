package com.khunchheang.photobookmark.ui.module

import com.khunchheang.photobookmark.ui.adapter.PhotoRecyclerAdapter
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
    fun providePhotoListPre(photoListInter: PhotoListInteractor): PhotoListPresenter {
        return PhotoListPresenterImpl(photoListInter)
    }

    @Provides
    @HomeFragmentScope
    fun providePhotoAdapter() = PhotoRecyclerAdapter()

}