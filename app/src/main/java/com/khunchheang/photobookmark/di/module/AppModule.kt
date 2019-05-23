package com.khunchheang.photobookmark.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import com.khunchheang.photobookmark.app.AppConstants
import com.khunchheang.photobookmark.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideIntent(): Intent = Intent()

    @Provides
    @Singleton
    fun provideAppDatabase(@Named(DB_NAME) dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Named(DB_NAME)
    @Singleton
    fun provideDatabaseName(): String = AppConstants.DB_NAME

    companion object {
        const val DB_NAME = "db_name"
    }
}