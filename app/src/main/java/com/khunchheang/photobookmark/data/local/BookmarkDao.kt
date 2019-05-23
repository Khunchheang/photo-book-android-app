package com.khunchheang.photobookmark.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmarkRoomModel: BookmarkRoomModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(lstBookmarkRoomModel: List<BookmarkRoomModel>)

    @Query("SELECT * FROM tb_bookmark ORDER BY photo_id ASC")
    fun loadAllBookmarkPhoto(): Flowable<List<BookmarkRoomModel>>

    @Query("DELETE FROM tb_bookmark WHERE photo_id = :photoId")
    fun removeFromBookmark(photoId: Long)

}