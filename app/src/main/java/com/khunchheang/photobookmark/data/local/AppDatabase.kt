package com.khunchheang.photobookmark.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.khunchheang.photobookmark.app.AppConstants

@Database(
    entities = [
        BookmarkRoomModel::class
    ],
    version = AppConstants.DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

}