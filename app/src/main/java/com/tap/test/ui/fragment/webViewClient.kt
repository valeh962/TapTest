package com.tap.test.ui.fragment

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.tap.mod_base.BaseActivity


class WebViewClient(private val activity: BaseActivity<*, *>) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        activity.showLoading()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        view?.loadUrl(
            "javascript:(function() { " +
                    "document.getElementById('header-bar').style.display='none';" +
                    "document.getElementById('tablist').style.display='none';" +
                    "})()"
        )

//        view?.loadUrl("javascript:(function(){var p=document.getElementsByClassName('body')[0];p.style.visibility='hidden';p.style.width=0;p.style.height=0;p.style.padding=0;p.style.margin=0;document.getElementsByClassName('td-fix-index')[0].style.visibility='visible';)();");


//        view?.loadUrl(
//            "javascript:window.HtmlViewer.showHTML" +
//                    "('<html>'+document.getElementsByClassName('compact-media-item')[0].innerHTML+'</html>');"
//        )

//        view?.evaluateJavascript(
//            "(function() { return ('<html>'+document.getElementsByClassName('compact-media-item')[0].innerHTML+'</html>'); })();"
//        ) { html ->
//            Log.e("HTML", html!!)
//            // code here
//        }

        activity.hideLoading()
    }


    open class MyJavaScriptInterface {

        fun showHTML(html: String?) {
            Log.e("__HTML", html ?: "null")

        }
    }
}