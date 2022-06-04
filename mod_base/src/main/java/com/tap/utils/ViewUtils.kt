package com.tap.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.tap.mod_base.BaseActivity

object ViewUtils {
    fun <T : BaseActivity<*, *>> Activity.openActivity(activity: Class<T>, bundle: Bundle? = null) {
        val intent = Intent(this, activity)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
        finish()
    }

    fun <T : BaseActivity<*, *>> Fragment.openActivity(activity: Class<T>, bundle: Bundle? = null) {
        requireActivity().run {
            val intent = Intent(this, activity)
            bundle?.let { intent.putExtras(it) }
            startActivity(intent)
            finish()
        }
    }

    fun Activity.hideKeyboard(currentView: View? = currentFocus) {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentView?.windowToken, 0)
    }

    fun Fragment.hideKeyboard(currentView: View? = requireActivity().currentFocus) {
        requireActivity().hideKeyboard(currentView)
    }

    fun View.showKeyboard(force: Boolean = false) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (force) {
            inputMethodManager.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}