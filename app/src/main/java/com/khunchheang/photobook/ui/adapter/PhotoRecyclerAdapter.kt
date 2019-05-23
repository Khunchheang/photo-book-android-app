package com.khunchheang.photobook.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.khunchheang.photobook.R
import com.khunchheang.photobook.data.PhotoItemResponse
import com.khunchheang.photobook.ui.base.adapter.BaseLoadMoreAdapter
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoRecyclerAdapter : BaseLoadMoreAdapter<PhotoItemResponse, PhotoRecyclerAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun setViewHolder(viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        if (context == null) context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindData(holder: ViewHolder, data: PhotoItemResponse, position: Int) {
        Glide.with(context!!)
            .load(data.listUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(holder.itemView.img_photo)

        holder.itemView.img_bookmark.setImageResource(
            if (data.isAddedBookmark) R.drawable.ic_bookmark_fill
            else R.drawable.ic_bookmark_border
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { itemClicked?.invoke(it, adapterPosition) }

            itemView.img_bookmark.setOnClickListener { itemClicked?.invoke(it, adapterPosition) }
        }

    }

    internal fun getPositionByPhotoId(photoId: Long): Int? {
        items.forEachIndexed { index, photoItemResponse ->
            if (photoItemResponse?.id == photoId.toString()) {
                photoItemResponse.isAddedBookmark = !photoItemResponse.isAddedBookmark
                return index
            }
        }
        return null
    }
}