package com.khunchheang.photobook.ui.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    internal var items = ArrayList<T>()

    internal var itemClicked: ((view: View, pos: Int) -> Unit)? = null

    internal fun setOnItemClickListener(itemClicked: (view: View, pos: Int) -> Unit) =
        apply { this.itemClicked = itemClicked }

    abstract fun setViewHolder(parent: ViewGroup, viewType: Int): VH

    abstract fun onBindData(holder: VH, data: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return setViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindData(holder, items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(data: ArrayList<T>) {
        items = data
        this.notifyDataSetChanged()
    }

    fun addItem(data: T) {
        items.add(data)
        this.notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        this.notifyDataSetChanged()
    }

    fun getItemPosition(position: Int): T {
        return items[position]
    }
}
