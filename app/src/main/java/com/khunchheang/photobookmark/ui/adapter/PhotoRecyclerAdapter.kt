package com.khunchheang.photobookmark.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.khunchheang.photobookmark.R
import com.khunchheang.photobookmark.data.PhotoItemResponse
import com.khunchheang.photobookmark.ui.base.adapter.BaseLoadMoreAdapter
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
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                itemClicked?.invoke(it, adapterPosition)
            }
        }

    }
}