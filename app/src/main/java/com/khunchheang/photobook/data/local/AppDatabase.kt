package com.khunchheang.photobook.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.khunchheang.photobook.app.AppConstants

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