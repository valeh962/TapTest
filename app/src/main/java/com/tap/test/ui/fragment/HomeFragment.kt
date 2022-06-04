package com.tap.test.ui.fragment

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebChromeClient
import com.tap.mod_base.BaseFragment
import com.tap.test.constants.Constants
import com.tap.test.databinding.FragmentHomeBinding
import com.tap.test.ui.activity.main.MainActivity
import com.tap.utils.QueryTextListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel, MainActivity>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java,
    isViewModelShared = false
) {

    @SuppressLint("JavascriptInterface")
    override fun setupView() {

        binding.apply {
            viewSearch.setOnQueryTextListener(object :
                QueryTextListener(this@HomeFragment.lifecycle,
                    onDebouncingQueryTextChange = {
                        it?.let { it1 ->
                            if (it.length > 3) loadURL(it1)
                        }
                    }) {})

            resultsRecycler.apply {

                settings.apply {
                    javaScriptEnabled = true
                }
                addJavascriptInterface(WebViewClient.MyJavaScriptInterface(), "HtmlViewer")

                webViewClient = WebViewClient(baseActivity)
                webChromeClient = WebChromeClient()
            }
        }
    }

    override fun observeData() {

    }

    private fun loadURL(text: String) {
        binding.resultsRecycler.loadUrl("${Constants.API_SEARCH}$text")
    }
}