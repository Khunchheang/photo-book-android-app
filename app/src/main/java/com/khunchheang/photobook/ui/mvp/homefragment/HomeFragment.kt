package com.khunchheang.photobook.ui.mvp.homefragment

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.khunchheang.photobook.R
import com.khunchheang.photobook.data.PhotoItemResponse
import com.khunchheang.photobook.ui.adapter.PhotoRecyclerAdapter
import com.khunchheang.photobook.ui.animator.SlideInUpAnimator
import com.khunchheang.photobook.ui.base.fragment.BaseMvpFragment
import com.khunchheang.photobook.ui.customview.ItemOffsetDecoration
import com.khunchheang.photobook.ui.extenstion.startPhotoDetailActivity
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobook.ui.mvp.homefragment.photolist.PhotoListPresenter
import com.khunchheang.photobook.ui.mvp.mainactivity.MainActivity
import com.khunchheang.photobook.ui.rxbus.RxBus
import com.khunchheang.photobook.ui.rxbus.RxEvent
import com.khunchheang.photobook.utils.NetworkUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.loading_progressbar.view.*
import javax.inject.Inject
import android.support.v4.app.ActivityOptionsCompat
import android.transition.Transition
import com.khunchheang.photobook.ui.animator.TransitionCallback

class HomeFragment : BaseMvpFragment(), HomeFragmentView {

    @Inject
    lateinit var photoListPre: PhotoListPresenter
    @Inject
    lateinit var photoAdapter: PhotoRecyclerAdapter
    @Inject
    lateinit var addBookmarkPre: AddBookmarkPresenter

    private var removeBookmarkDisposable: Disposable? = null
    private lateinit var mainActivity: MainActivity
    private val sharedExitListener = object : TransitionCallback() {
        override fun onTransitionEnd(transition: Transition) {
            setExitSharedElementCallback(null)
        }
    }

    override val layoutResource = R.layout.fragment_home

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onCreateViewInflated(inflatedView: View, savedInstanceState: Bundle?) {
        mainActivity.window.sharedElementExitTransition.addListener(sharedExitListener)
        photoListPre.attach(this)
        addBookmarkPre.attach(this)

        initSwipeRefresh()
        initPhotoList()
        setRxBusRemoveBookmark()

        view!!.btn_retry.setOnClickListener {
            view!!.btn_retry.visibility = View.GONE
            photoListPre.getPhotoList()
        }
    }

    override fun showLoading() {
        if (!view!!.swipe_refresh.isRefreshing) view!!.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        view!!.progressBar.visibility = View.GONE
    }

    override fun showError(msg: Int) {
        super.showError(msg)
        photoAdapter.isLoading = !NetworkUtil.isNetworkAvailable(context)
        photoAdapter.removeBottomPb()

        if (photoAdapter.offset == 1) view!!.btn_retry.visibility = View.VISIBLE

        if (!NetworkUtil.isNetworkAvailable(context)) {
            photoAdapter.isRetry = true
            photoAdapter.enableLoadingBottom()
        }

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
                else addBookmarkPre.addPhotoBookmark(pos, itemClicked.id, itemClicked.url, itemClicked.downloadUrl)
            } else {
                val option = ActivityOptionsCompat.makeSceneTransitionAnimation(mainActivity, view, view.transitionName)
                startPhotoDetailActivity(
                    mainActivity,
                    itemClicked.id!!.toLong(),
                    itemClicked.url!!,
                    itemClicked.isAddedBookmark,
                    itemClicked.listUrl,
                    itemClicked.downloadUrl!!,
                    view.transitionName,
                    option
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