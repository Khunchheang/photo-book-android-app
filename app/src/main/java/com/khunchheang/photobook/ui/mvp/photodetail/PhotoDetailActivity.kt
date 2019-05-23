package com.khunchheang.photobook.ui.mvp.photodetail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.style.URLSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.khunchheang.photobook.R
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkView
import com.khunchheang.photobook.ui.rxbus.RxBus
import com.khunchheang.photobook.ui.rxbus.RxEvent
import com.odic.skybooking.ui.base.activity.BaseSupportToolbarActivity
import kotlinx.android.synthetic.main.activity_photo_detail.*
import kotlinx.android.synthetic.main.loading_progressbar.*
import javax.inject.Inject

class PhotoDetailActivity : BaseSupportToolbarActivity(), AddBookmarkView {

    @Inject
    lateinit var addBookmarkPre: AddBookmarkPresenter
    @Inject
    lateinit var activityIntent: Intent

    private var isAddedBookmark: Boolean = false
    private var photoId: Long? = null
    private var url: String? = null
    private var photoListUrl: String? = null
    private var downloadUrl: String? = null
    private var menuItem: Menu? = null
    private var imageLoadingListener: RequestListener<Drawable>? = null
    private var isActivityActive: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        setSupportToolbar()

        getIntentValue()
        title = "${getString(R.string.photo_id)} $photoId"

        img_full.transitionName = intent.getStringExtra(TRANSITION_NAME)

        loadImage()
        setTextUrl()

        addBookmarkPre.attach(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.photo_detail, menu)
        this.menuItem = menu
        setOptionMenuIcon(
            if (isAddedBookmark) R.drawable.ic_bookmark_fill_white
            else R.drawable.ic_bookmark_border_white
        )
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_add_bookmark) {
            if (isAddedBookmark) {
                addBookmarkPre.removePhotoBookmark(photoId = photoId.toString())
            } else {
                addBookmarkPre.addPhotoBookmark(photoId = photoId.toString(), url = url, downloadUrl = downloadUrl)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAddBookmarkSuccess(pos: Int) {
        isAddedBookmark = true
        setOptionMenuIcon(R.drawable.ic_bookmark_fill_white)
        RxBus.publish(RxEvent.EventRemoveFromBookmark(photoId!!))
    }

    override fun onRemoveBookmarkSuccess(pos: Int) {
        isAddedBookmark = false
        setOptionMenuIcon(R.drawable.ic_bookmark_border_white)
        RxBus.publish(RxEvent.EventRemoveFromBookmark(photoId!!))
    }

    override fun onPhotoIdError(msg: Int) {
        showToast(msg)
    }

    override fun onStop() {
        super.onStop()
        isActivityActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        addBookmarkPre.detach()
        imageLoadingListener = null
    }

    private fun setOptionMenuIcon(icon: Int) {
        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(applicationContext, icon)
        invalidateOptionsMenu()
    }

    private fun getIntentValue() {
        photoId = intent.getLongExtra(PHOTO_ID, 0)
        url = intent.getStringExtra(URL)
        isAddedBookmark = intent.getBooleanExtra(IS_ADDED_BOOKMARK, false)
        photoListUrl = intent.getStringExtra(LIST_URL)
        downloadUrl = intent.getStringExtra(DOWNLOAD_URL)
    }

    private fun loadImage() {
        initImageLoadingListener()

        Glide.with(applicationContext)
            .load(downloadUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .listener(imageLoadingListener)
            .into(img_full)
    }

    private fun setTextUrl() {
        val spannableString = SpannableString(url)
        spannableString.setSpan(URLSpan(""), 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_url.setText(spannableString, TextView.BufferType.SPANNABLE)

        tv_url.setOnClickListener {
            activityIntent.action = Intent.ACTION_VIEW
            activityIntent.data = Uri.parse(url)
            startActivity(activityIntent)
        }
    }

    private fun initImageLoadingListener() {
        imageLoadingListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                if (isActivityActive) {
                    showDialogMessage(getString(R.string.sorry_cannot_load_this_image)).show(false)
                }
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                img_full.setImageDrawable(resource)
                return true
            }
        }
    }

    companion object {
        const val PHOTO_ID = "photo_id"
        const val LIST_URL = "photo_list_url"
        const val URL = "url"
        const val IS_ADDED_BOOKMARK = "is_added_bookmark"
        const val DOWNLOAD_URL = "download_url"
        const val TRANSITION_NAME = "transition_name"
    }
}