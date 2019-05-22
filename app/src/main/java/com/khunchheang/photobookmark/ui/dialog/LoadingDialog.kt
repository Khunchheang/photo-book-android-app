package com.khunchheang.photobookmark.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.khunchheang.photobookmark.R
import com.khunchheang.photobookmark.ui.base.fragment.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog : BaseDialogFragment() {

    companion object {
        private var dialogLoading: LoadingDialog? = null

        @Synchronized
        fun newInstance(msg: Int = R.string.loading): LoadingDialog {
            if (dialogLoading == null) dialogLoading = LoadingDialog()

            val args = Bundle()
            args.putInt("msg", msg)
            dialogLoading!!.arguments = args

            return dialogLoading!!
        }
    }

    override val layoutId = R.layout.dialog_loading

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val msg = arguments?.getInt("msg", 0)
        txt_msg.text = msg?.let { getString(it) }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        dialogLoading = null
    }
}
