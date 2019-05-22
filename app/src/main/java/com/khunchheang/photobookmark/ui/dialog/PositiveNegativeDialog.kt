package com.khunchheang.photobookmark.ui.dialog

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.khunchheang.photobookmark.R
import com.khunchheang.photobookmark.ui.base.fragment.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_positive_negative.*

class PositiveNegativeDialog : BaseDialogFragment(), View.OnClickListener {

    private var positiveClickListener: (() -> Unit)? = null
    private var negativeClickListener: (() -> Unit)? = null
    private var positiveButtonText: String? = null
    private var negativeButtonText: String? = null
    private var message: String? = null
    private var title: String? = null

    override val layoutId = R.layout.dialog_positive_negative

    private fun newInstance(
        positiveClickListener: (() -> Unit)? = null,
        negativeClickListener: (() -> Unit)? = null,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        message: String? = null,
        title: String? = null
    ) {
        this.positiveClickListener = positiveClickListener
        this.negativeClickListener = negativeClickListener
        this.positiveButtonText = positiveButtonText
        this.negativeButtonText = negativeButtonText
        this.message = message
        this.title = title
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_msg.text = message

        if (title != null) {
            txt_title.text = title
            txt_title.visibility = View.VISIBLE
        }

        if (positiveButtonText != null) btn_positive.text = positiveButtonText

        if (negativeButtonText != null) {
            btn_negative.text = negativeButtonText
            btn_negative.visibility = View.VISIBLE
        }

        btn_positive.setOnClickListener(this)
        btn_negative.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_positive) positiveClickListener?.invoke()
        else if (view.id == R.id.btn_negative) negativeClickListener?.invoke()
        dialog.dismiss()
        clearReference()
    }

    private fun clearReference() {
        positiveClickListener = null
        negativeClickListener = null
        positiveButtonText = null
        negativeButtonText = null
        message = null
        title = null
    }

    data class Builder(private val fragmentManager: FragmentManager) {

        private var positiveClickListener: (() -> Unit)? = null
        private var negativeClickListener: (() -> Unit)? = null
        private var positiveButtonText: String? = null
        private var negativeButtonText: String? = null
        private var message: String? = null
        private var title: String? = null

        fun setMessage(msg: String) = apply { this.message = msg }

        fun setOnPositiveClickListener(positiveClickListener: () -> Unit) =
            apply { this.positiveClickListener = positiveClickListener }

        fun setOnNegativeClickListener(ngClick: (() -> Unit)) = apply { this.negativeClickListener = ngClick }

        fun setTextButtonPositive(txt: String) = apply { this.positiveButtonText = txt }

        fun setWithNegativeButton(txt: String) = apply { this.negativeButtonText = txt }

        fun setTitle(txt: String) = apply { this.title = txt }

        fun show(isCancelable: Boolean) {
            val dialogFragment = build()
            dialogFragment.isCancelable = isCancelable
            if (!dialogFragment.isAdded) dialogFragment.show(fragmentManager, "dialog")
        }

        fun build(): PositiveNegativeDialog {
            val dialog = PositiveNegativeDialog()
            dialog.newInstance(
                positiveClickListener,
                negativeClickListener,
                positiveButtonText,
                negativeButtonText,
                message,
                title
            )

            clearBuilderReferences()

            return dialog
        }

        private fun clearBuilderReferences() {
            positiveClickListener = null
            negativeClickListener = null
            positiveButtonText = null
            negativeButtonText = null
            message = null
            title = null
        }
    }
}