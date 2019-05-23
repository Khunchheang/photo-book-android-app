package com.khunchheang.photobookmark.ui.extenstion

import android.content.Context
import android.content.Intent
import com.khunchheang.photobookmark.ui.mvp.photodetail.PhotoDetailActivity

fun startPhotoDetailActivity(
    context: Context,
    photoId: Long,
    isAddedBookmark: Boolean,
    listUrl: String,
    photoUrl: String
) {
    val intent = Intent(context, PhotoDetailActivity::class.java)
    intent.putExtra(PhotoDetailActivity.PHOTO_ID, photoId)
    intent.putExtra(PhotoDetailActivity.IS_ADDED_BOOKMARK, isAddedBookmark)
    intent.putExtra(PhotoDetailActivity.LIST_URL, listUrl)
    intent.putExtra(PhotoDetailActivity.DOWNLOAD_URL, photoUrl)
    context.startActivity(intent)
}