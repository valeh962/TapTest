package com.tap.view

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.tap.mod_base.R
import com.tap.utils.ThreadManager

class LoadingView constructor(
    private val threadManager: ThreadManager,
    private val activity: AppCompatActivity
) {

    private var loadingDialog = Dialog(activity).apply {
        setOnKeyListener { _: DialogInterface, keyCode: Int, keyEvent: KeyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                activity.onBackPressed()
                dismiss()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.view_loading)
    }

    fun showLoading() {
        loadingDialog.let {
            try {
                if (!it.isShowing) {
                    it.show()
                    threadManager.postUI({ hideLoading() }, 14000)
                }
            } catch (ignored: Exception) {
            }
        }
    }

    fun hideLoading() {
        loadingDialog.let {
            if (it.isShowing)
                threadManager.postUI({
                    try {
                        it.dismiss()
                    } catch (ignored: Exception) {
                    }
                }, 300)

        }
    }
}