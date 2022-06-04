package com.tap.view

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.tap.mod_base.R

fun View?.showToast(message: String?) {
    this?.let {
        when (it) {
            is RelativeLayout -> RelativeLayoutToast(it, message)
            is ConstraintLayout -> ConstraintLayoutToast(it, message)
            else -> Toast.makeText(it.context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("ViewConstructor")
class RelativeLayoutToast @JvmOverloads constructor(
    private val parent: RelativeLayout?,
    message: String?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(parent?.context, attrs, defStyleAttr), BaseToast {

    init {
        parent?.addView(view(context, this, message))
    }

    override fun removeView(view: View?) {
        parent?.removeView(view)
    }
}

@SuppressLint("ViewConstructor")
class ConstraintLayoutToast @JvmOverloads constructor(
    private val parent: ConstraintLayout?,
    message: String?, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : RelativeLayout(parent?.context, attrs, defStyleAttr), BaseToast {

    init {
        parent?.addView(view(context, this, message))
    }

    override fun removeView(view: View?) {
        parent?.removeView(view)
    }

}

private interface BaseToast {

    fun view(context: Context, viewGroup: ViewGroup, message: String?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.view_snackbar, viewGroup)
        if (message?.isNotEmpty() == true) {
            viewGroup.elevation = 120f
            view.apply {
                findViewById<TextView>(R.id.message)?.text = message
                animation = AnimationUtils.loadAnimation(context, R.anim.message_enter)
                Handler(Looper.getMainLooper()).postDelayed({ hideToast(view) }, 2000)
                setOnClickListener {
                    hideToast(view)
                }
            }
        }
        return view
    }

    private fun hideToast(view: View?) {
        view?.animate()?.apply {
            translationY(1000f).setListener(
                object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        removeView(view)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        removeView(view)
                    }

                    override fun onAnimationRepeat(animation: Animator?) {}
                }
            )
        }
    }

    fun removeView(view: View?)


}