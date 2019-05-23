package com.khunchheang.photobookmark.data.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tb_bookmark")
class BookmarkRoomModel {

    @PrimaryKey
    @ColumnInfo(name = "bookmark_id")
    var id: Long? = null

    @NotNull
    @ColumnInfo(name = "photo_id")
    var photoId: Long? = null

    @NotNull
    @ColumnInfo(name = "photo_list_url")
    var listUrl: String? = null

    @NotNull
    @ColumnInfo(name = "download_url")
    var downloadUrl: String? = null

}