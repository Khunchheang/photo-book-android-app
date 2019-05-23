package com.khunchheang.photobook.ui.base.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khunchheang.photobook.R
import com.khunchheang.photobook.app.AppConstants
import kotlinx.android.synthetic.main.load_more_progressbar.view.*

abstract class BaseLoadMoreAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var loadFirstListener: (() -> Unit)? = null
    private var loadMoreListener: ((offset: Int) -> Unit)? = null
    private var lastVisibleItem: Int = 0

    private var totalItemCount: Int = 0
    private var isAll = false
    internal var isRetry = false
    internal var recyclerView: RecyclerView? = null
    internal var isLoading = true
    internal var items = ArrayList<T?>()
    internal var offset = 1

    internal var itemClicked: ((view: View, pos: Int) -> Unit)? = null

    internal fun setOnItemClickListener(itemClicked: (view: View, pos: Int) -> Unit) =
        apply { this.itemClicked = itemClicked }

    abstract fun setViewHolder(viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun onBindData(holder: VH, data: T, position: Int)

    fun startPagination() {
        if (this.itemCount == 0) {
            isLoading = true
            loadFirstListener?.invoke()
        }

        val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = layoutManager.itemCount
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (itemCount != 0 && !isAll && !isLoading && totalItemCount <= (lastVisibleItem + AppConstants.QUERY_LIMIT)) {
                    isLoading = true
                    enableLoadingBottom()
                    loadMoreListener?.invoke(offset)
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) VIEW_ITEM else VIEW_PRO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM) {
            setViewHolder(viewType, parent)
        } else {
            ProgressViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.load_more_progressbar, parent, false)
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseLoadMoreAdapter<*, *>.ProgressViewHolder) {
            holder.itemView.progressbar!!.visibility = if (isAll || isRetry) View.GONE else View.VISIBLE
            holder.itemView.txt_msg!!.visibility = if (isAll) View.VISIBLE else View.GONE
            holder.itemView.btn_retry!!.visibility = if (isRetry && !isAll) View.VISIBLE else View.GONE
        } else {
            val viewHolder = holder as VH
            onBindData(viewHolder, items[position]!!, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    internal open fun addItems(dataItem: List<T>) {
        removeBottomPb()
        isAll = dataItem.size < AppConstants.QUERY_LIMIT
        isLoading = false
        if (dataItem.isNotEmpty()) items.addAll(dataItem)
        if (isAll) enableLoadingBottom()
        notifyItemInserted(itemCount)
    }

    internal fun removeBottomPb() {
        if (itemCount != 0) {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size)
        }
    }

    internal fun enableLoadingBottom() {
        if (itemCount != 0) {
            items.add(null)
            recyclerView?.post { notifyItemInserted(itemCount) }
        }
    }

    fun clear() {
        isAll = false
        items.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return items[position]!!
    }

    fun getAllItems(): ArrayList<T?> {
        return items
    }

    fun setOnLoadFirstListener(loadFirstListener: (() -> Unit)) = apply {
        this.loadFirstListener = loadFirstListener
    }

    fun setOnLoadMoreListener(loadMoreListener: ((offset: Int) -> Unit)) = apply {
        this.loadMoreListener = loadMoreListener
    }

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.btn_retry.setOnClickListener {
                isRetry = false
                isLoading = false
                removeBottomPb()
                recyclerView?.smoothScrollToPosition(itemCount)
                loadMoreListener?.invoke(offset)
            }
        }

    }

    companion object {
        private const val VIEW_ITEM = 1
        private const val VIEW_PRO = 0
    }
}