package com.khunchheang.photobookmark.ui.mvp.homefragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.khunchheang.photobookmark.R
import com.khunchheang.photobookmark.data.PhotoItemResponse
import com.khunchheang.photobookmark.ui.adapter.PhotoRecyclerAdapter
import com.khunchheang.photobookmark.ui.animator.SlideInUpAnimator
import com.khunchheang.photobookmark.ui.base.fragment.BaseMvpFragment
import com.khunchheang.photobookmark.ui.customview.ItemOffsetDecoration
import com.khunchheang.photobookmark.ui.mvp.homefragment.photolist.PhotoListPresenter
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.loading_progressbar.view.*
import javax.inject.Inject

class HomeFragment : BaseMvpFragment(), HomeFragmentView {

    @Inject
    lateinit var photoListPre: PhotoListPresenter
    @Inject
    lateinit var photoAdapter: PhotoRecyclerAdapter

    override val layoutResource = R.layout.fragment_home

    override fun onCreateViewInflated(inflatedView: View, savedInstanceState: Bundle?) {
        photoListPre.attach(this)

        initSwipeRefresh()
        initPhotoList()
    }

    override fun showLoading() {
        if (!view!!.swipe_refresh.isRefreshing) view!!.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        view!!.progressBar.visibility = View.GONE
    }

    override fun showError(msg: Int) {
        super.showError(msg)
        photoAdapter.isLoading = false
        photoAdapter.removeBottomPb()

        view!!.swipe_refresh.isRefreshing = false
    }

    override fun onPhotoListResponse(data: List<PhotoItemResponse>) {
        photoAdapter.addItems(data)
        photoAdapter.offset++

        view!!.swipe_refresh.isRefreshing = false
    }

    private fun initPhotoList() {
        val spacing = resources.getDimensionPixelOffset(R.dimen.default_spacing_small)
        val decoration = ItemOffsetDecoration(spacing)
        decoration.setPlusSpacingTop(spacing)
        view!!.recycler_photo.setHasFixedSize(true)
        view!!.recycler_photo.layoutManager = LinearLayoutManager(context)
        view!!.recycler_photo.adapter = photoAdapter
        view!!.recycler_photo.itemAnimator = SlideInUpAnimator()
        view!!.recycler_photo.addItemDecoration(decoration)
        photoAdapter.recyclerView = view!!.recycler_photo

        setPhotoItemClick()

        photoAdapter.setOnLoadFirstListener {
            photoListPre.getPhotoList()
        }

        photoAdapter.setOnLoadMoreListener { offset ->
            photoListPre.getPhotoList(offset)
        }

        photoAdapter.startPagination()
    }

    private fun setPhotoItemClick() {
        photoAdapter.setOnItemClickListener { _, pos ->
            showToast(photoAdapter.getItem(pos).downloadUrl.toString())
        }
    }

    private fun initSwipeRefresh() {
        view!!.swipe_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorAccent))
        view!!.swipe_refresh.setOnRefreshListener {
            if (photoAdapter.itemCount == 0) {
                view!!.swipe_refresh.isRefreshing = false
                return@setOnRefreshListener
            }

            photoAdapter.clear()
            photoAdapter.startPagination()
        }
    }
}