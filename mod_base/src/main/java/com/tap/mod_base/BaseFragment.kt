package com.tap.mod_base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.tap.utils.Inflate
import com.tap.utils.ViewUtils.hideKeyboard
import com.tap.utils.navOptionsSlideRight
import com.tap.utils.navigateToFragment

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding, VM : ViewModel, AC : BaseActivity<*, *>>(
    private val vb: Inflate<VB>,
    private val vm: Class<VM>,
    private val isViewModelShared: Boolean?
) : Fragment() {

    val baseActivity by lazy { requireActivity() as AC }

    protected val threadManager by lazy { baseActivity.threadManager }
    protected val rsc by lazy { baseActivity.resourceProvider }

    protected var isObserving = false

    protected var _binding: VB? = null
    protected lateinit var viewModel: VM

    val binding get() = _binding!!

    abstract fun setupView()
    abstract fun observeData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = vb.invoke(inflater, container, false)

        setupView()
        if (!isObserving) {
            isObserving = true
            observeData()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity.onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBack()
                }
            })
        viewModel = ViewModelProvider(
            if (isViewModelShared == true) requireActivity()
            else this
        )[vm]
    }

    fun showLoading() {
        baseActivity.showLoading()
    }

    fun hideLoading() {
        baseActivity.hideLoading()
    }

    fun navigate(
        navDirection: NavDirections,
        bundle: Bundle? = null,
        options: NavOptions.Builder? = navOptionsSlideRight
    ) {
        findNavController().navigateToFragment(navDirection, bundle, options)
    }

    fun navigate(
        fragmentID: Int, bundle: Bundle? = null, options: NavOptions.Builder? = navOptionsSlideRight
    ) {
        hideKeyboard()
        findNavController().navigateToFragment(fragmentID, bundle, options)
    }

    fun postUI(runnable: Runnable, time: Long? = 0L) {
        threadManager.postUI(runnable, time)
    }

    fun postBack(runnable: Runnable) {
        threadManager.postBack(runnable)
    }

    fun scopeIO() = threadManager.scopeIO()

    fun scopeUI() = threadManager.scopeUI()

    open fun onBack() {
        baseActivity.onBack()
    }

    fun showMessage(message: String?) {
        baseActivity.showMessage(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cleanFragment()
    }

    open fun cleanFragment() {
        postBack {
            System.gc()
            Runtime.getRuntime().gc()
        }
    }
}