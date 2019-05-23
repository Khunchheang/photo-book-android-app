package com.khunchheang.photobook.ui.extenstion

import android.content.Context
import android.content.Intent
import android.util.Pair
import android.widget.ImageView
import com.khunchheang.photobook.ui.mvp.photodetail.PhotoDetailActivity
import android.support.v4.app.ActivityOptionsCompat


fun startPhotoDetailActivity(
    context: Context,
    photoId: Long,
    url: String,
    isAddedBookmark: Boolean,
    listUrl: String,
    photoUrl: String,
    transitionName: String,
    optionTransition: ActivityOptionsCompat
) {
    val intent = Intent(context, PhotoDetailActivity::class.java)
    intent.putExtra(PhotoDetailActivity.PHOTO_ID, photoId)
    intent.putExtra(PhotoDetailActivity.IS_ADDED_BOOKMARK, isAddedBookmark)
    intent.putExtra(PhotoDetailActivity.URL, url)
    intent.putExtra(PhotoDetailActivity.LIST_URL, listUrl)
    intent.putExtra(PhotoDetailActivity.DOWNLOAD_URL, photoUrl)
    intent.putExtra(PhotoDetailActivity.TRANSITION_NAME, transitionName)

    context.startActivity(intent, optionTransition.toBundle())
}