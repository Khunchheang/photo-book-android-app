package com.khunchheang.photobookmark.ui.animator

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import com.khunchheang.photobookmark.ui.animator.BaseItemAnimator

@Suppress("DEPRECATION")
class SlideInUpAnimator : BaseItemAnimator() {

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        ViewCompat.animate(holder.itemView)
            .translationY(holder.itemView.height.toFloat())
            .alpha(0f)
            .setDuration(removeDuration)
            .setListener(DefaultRemoveVpaListener(holder))
            .start()
    }

    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
        ViewCompat.setTranslationY(holder.itemView, holder.itemView.height.toFloat())
        ViewCompat.setAlpha(holder.itemView, 0f)
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        ViewCompat.animate(holder.itemView)
            .translationY(0f)
            .alpha(1f)
            .setDuration(addDuration)
            .setListener(DefaultAddVpaListener(holder))
            .start()
    }
}
