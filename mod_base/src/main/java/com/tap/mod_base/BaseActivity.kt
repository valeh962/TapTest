package com.tap.mod_base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.viewbinding.ViewBinding
import com.tap.utils.Bind
import com.tap.utils.Resources
import com.tap.utils.ThreadManager
import com.tap.utils.ViewUtils.hideKeyboard
import com.tap.view.LoadingView
import com.tap.view.showToast
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    private val vb: Bind<VB>,
    private val vm: Class<VM>,
) : AppCompatActivity() {

    @Inject
    lateinit var threadManager: ThreadManager

    @Inject
    lateinit var resourceProvider: Resources

    lateinit var loadingView: LoadingView

    var savedInstanceState: Bundle? = null

    private var _binding: VB? = null
    protected lateinit var viewModel: VM
    private var isActive = false

    val binding get() = _binding!!

    abstract fun setupView()
    abstract fun observeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null)
            this.savedInstanceState = savedInstanceState
        loadingView = LoadingView(threadManager, this)
        _binding = vb.invoke(layoutInflater)
        setContentView(_binding?.root)
        viewModel = ViewModelProvider(this)[vm]
        setupView()
        observeData()
    }


    fun showMessage(message: String?) {
        binding.root.showToast(message)
    }

    open fun showLoading() {
        loadingView.showLoading()
    }

    open fun hideLoading() {
        loadingView.hideLoading()
    }

    fun scopeIO() = threadManager.scopeIO()

    fun scopeUI() = threadManager.scopeUI()

    fun postUI(runnable: Runnable, time: Long? = 0L) = threadManager.postUI(runnable, time)

    fun postBack(runnable: Runnable) = threadManager.postBack(runnable)

    open fun onBack() {
        hideKeyboard()
        hideLoading()
        finish()
    }

    open fun navigate(
        navDirection: NavDirections, bundle: Bundle?, options: NavOptions.Builder? = null
    ) {
    }

    override fun onStart() {
        super.onStart()
        isActive = true
    }

    override fun onStop() {
        super.onStop()
        isActive = false
    }

    override fun finish() {
        hideKeyboard()
        super.finish()
    }

    override fun onBackPressed() {
        hideKeyboard()
        super.onBackPressed()
    }

}