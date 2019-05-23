package com.khunchheang.photobook.ui.mvp.bookmarkfragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.view.View
import com.khunchheang.photobook.R
import com.khunchheang.photobook.data.local.BookmarkRoomModel
import com.khunchheang.photobook.ui.adapter.BookmarkRecyclerAdapter
import com.khunchheang.photobook.ui.animator.SlideInUpAnimator
import com.khunchheang.photobook.ui.animator.TransitionCallback
import com.khunchheang.photobook.ui.base.fragment.BaseMvpFragment
import com.khunchheang.photobook.ui.customview.ItemOffsetDecoration
import com.khunchheang.photobook.ui.extenstion.startPhotoDetailActivity
import com.khunchheang.photobook.ui.mvp.bookmarkfragment.getbookmark.GetBookmarkPresenter
import com.khunchheang.photobook.ui.mvp.homefragment.addbookmark.AddBookmarkPresenter
import com.khunchheang.photobook.ui.mvp.mainactivity.MainActivity
import com.khunchheang.photobook.ui.rxbus.RxBus
import com.khunchheang.photobook.ui.rxbus.RxEvent
import kotlinx.android.synthetic.main.fragment_bookmark.view.*
import kotlinx.android.synthetic.main.loading_progressbar.view.*
import javax.inject.Inject

class BookmarkFragment : BaseMvpFragment(), BookmarkFragmentView {

    @Inject
    lateinit var bookmarkAdapter: BookmarkRecyclerAdapter
    @Inject
    lateinit var addBookmarkPre: AddBookmarkPresenter
    @Inject
    lateinit var getBookmarkPre: GetBookmarkPresenter

    private var isBookmarkRemoving: Boolean = false
    private lateinit var mainActivity: MainActivity
    private val sharedExitListener = object : TransitionCallback() {
        override fun onTransitionEnd(transition: Transition) {
            setExitSharedElementCallback(null)
        }
    }

    override val layoutResource = R.layout.fragment_bookmark

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onCreateViewInflated(inflatedView: View, savedInstanceState: Bundle?) {
        mainActivity.window.sharedElementExitTransition.addListener(sharedExitListener)
        addBookmarkPre.attach(this)
        getBookmarkPre.attach(this)

        initBookmarkList()
        getBookmarkPre.getPhotoBookmark()
    }

    override fun onBookmarkResponse(data: ArrayList<BookmarkRoomModel>) {
        view!!.progressBar.visibility = View.GONE
        if (data.isEmpty()) view!!.tv_no_bookmark.visibility = View.VISIBLE
        else view!!.tv_no_bookmark.visibility = View.GONE

        if (!isBookmarkRemoving) {
            if (bookmarkAdapter.itemCount != 0) bookmarkAdapter.clearItems()
            bookmarkAdapter.items.addAll(data)
            bookmarkAdapter.notifyItemInserted(bookmarkAdapter.itemCount)
        }
    }

    override fun onAddBookmarkSuccess(pos: Int) {}

    override fun onRemoveBookmarkSuccess(pos: Int) {
        if (bookmarkAdapter.itemCount == 0) view!!.tv_no_bookmark.visibility = View.VISIBLE

        bookmarkAdapter.getItemPosition(pos).photoId?.let { RxEvent.EventRemoveFromBookmark(it) }?.let {
            RxBus.publish(it)
        }

        bookmarkAdapter.items.removeAt(pos)
        bookmarkAdapter.notifyItemRemoved(pos)
        isBookmarkRemoving = false
    }

    override fun onPhotoIdError(msg: Int) {
        showToast(msg)
    }

    override fun onDetach() {
        super.onDetach()
        addBookmarkPre.detach()
        getBookmarkPre.detach()
    }

    private fun initBookmarkList() {
        val spacing = resources.getDimensionPixelOffset(R.dimen.default_spacing_small)
        val decoration = ItemOffsetDecoration(spacing)
        decoration.setPlusSpacingTop(spacing)
        view!!.recycler_photo.setHasFixedSize(true)
        view!!.recycler_photo.layoutManager = LinearLayoutManager(context)
        view!!.recycler_photo.adapter = bookmarkAdapter
        view!!.recycler_photo.itemAnimator = SlideInUpAnimator()
        view!!.recycler_photo.addItemDecoration(decoration)

        setPhotoItemClick()
    }

    private fun setPhotoItemClick() {
        bookmarkAdapter.setOnItemClickListener { view, pos ->
            val itemClicked = bookmarkAdapter.getItemPosition(pos)
            if (view.id == R.id.img_bookmark) {
                isBookmarkRemoving = true
                addBookmarkPre.removePhotoBookmark(pos, itemClicked.photoId.toString())
            } else {
                val option = ActivityOptionsCompat.makeSceneTransitionAnimation(mainActivity, view, view.transitionName)
                startPhotoDetailActivity(
                    mainActivity,
                    itemClicked.photoId!!.toLong(),
                    itemClicked.url!!,
                    true,
                    itemClicked.listUrl!!,
                    itemClicked.downloadUrl!!,
                    view.transitionName,
                    option
                )
            }
        }
    }
}