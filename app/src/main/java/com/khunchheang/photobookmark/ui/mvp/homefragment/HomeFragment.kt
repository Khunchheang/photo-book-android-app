package com.khunchheang.photobookmark.ui.mvp.homefragment

import android.content.Context
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
import com.khunchheang.photobookmark.ui.extenstion.startPhotoDetailActivity
import com.khunchheang.photobookmark.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobookmark.ui.mvp.homefragment.photolist.PhotoListPresenter
import com.khunchheang.photobookmark.ui.mvp.mainactivity.MainActivity
import com.khunchheang.photobookmark.ui.rxbus.RxBus
import com.khunchheang.photobookmark.ui.rxbus.RxEvent
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.loading_progressbar.view.*
import javax.inject.Inject

class HomeFragment : BaseMvpFragment(), HomeFragmentView {

    @Inject
    lateinit var photoListPre: PhotoListPresenter
    @Inject
    lateinit var photoAdapter: PhotoRecyclerAdapter
    @Inject
    lateinit var addBookmarkPre: AddBookmarkPresenter

    private var removeBookmarkDisposable: Disposable? = null
    private lateinit var mainActivity: MainActivity

    override val layoutResource = R.layout.fragment_home

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onCreateViewInflated(inflatedView: View, savedInstanceState: Bundle?) {
        photoListPre.attach(this)
        addBookmarkPre.attach(this)

        initSwipeRefresh()
        initPhotoList()
        setRxBusRemoveBookmark()
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

    override fun onPhotoIdError(msg: Int) {
        showToast(msg)
    }

    override fun onAddBookmarkSuccess(pos: Int) {
        photoAdapter.getItem(pos).isAddedBookmark = true
        photoAdapter.notifyItemChanged(pos)
    }

    override fun onRemoveBookmarkSuccess(pos: Int) {
        photoAdapter.getItem(pos).isAddedBookmark = false
        photoAdapter.notifyItemChanged(pos)
    }

    override fun onDetach() {
        super.onDetach()
        photoListPre.detach()
        addBookmarkPre.detach()
        removeBookmarkDisposable?.dispose()
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
        photoAdapter.setOnItemClickListener { view, pos ->
            val itemClicked = photoAdapter.getItem(pos)
            if (view.id == R.id.img_bookmark) {
                if (itemClicked.isAddedBookmark) addBookmarkPre.removePhotoBookmark(pos, itemClicked.id)
                else addBookmarkPre.addPhotoBookmark(pos, itemClicked.id, itemClicked.downloadUrl)
            } else {
                startPhotoDetailActivity(
                    mainActivity,
                    itemClicked.id!!.toLong(),
                    itemClicked.isAddedBookmark,
                    itemClicked.listUrl,
                    itemClicked.downloadUrl!!
                )
            }
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

    private fun setRxBusRemoveBookmark() {
        removeBookmarkDisposable = RxBus.listen(RxEvent.EventRemoveFromBookmark::class.java)
            .subscribe {
                val position = photoAdapter.getPositionByPhotoId(it.photoId)
                position?.let { pos -> photoAdapter.notifyItemChanged(pos) }
            }
    }
}