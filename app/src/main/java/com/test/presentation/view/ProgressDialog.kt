package com.test.presentation.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.test.databinding.DialogProgressBinding
import com.test.presentation.base.BaseDialogFragment

class ProgressDialog : BaseDialogFragment<DialogProgressBinding>() {

    companion object {

        private fun newInstance(): ProgressDialog {
            return ProgressDialog()
        }

        fun show(fragmentManager: FragmentManager): ProgressDialog {
            val dialog = newInstance().apply {
                isCancelable = false
                show(fragmentManager, this@Companion::class.java.simpleName)
            }
            return dialog
        }
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogProgressBinding = DialogProgressBinding.inflate(inflater, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onCreateDialog = super.onCreateDialog(savedInstanceState).apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return onCreateDialog
    }

    override fun DialogProgressBinding.onBindViews() {}
}