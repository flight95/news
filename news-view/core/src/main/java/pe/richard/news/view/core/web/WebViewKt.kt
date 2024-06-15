package pe.richard.news.view.core.web

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import pe.richard.library.domain.model.core.primitive.toUri

fun WebView.initializeWebSettings() {
    settings.apply {
        setSupportMultipleWindows(true)
        setSupportZoom(false)
        cacheMode = WebSettings.LOAD_NO_CACHE
        javaScriptEnabled = false
        displayZoomControls = false
    }
}

fun WebView.initializeWebChromeClient() {
    webChromeClient = object : WebChromeClient() {}
}

fun WebView.initializeWebViewClient(
    overrideUrlLoading: ((view: WebView?, uri: Uri?) -> Boolean?)? = null,
    pageStarted: ((view: WebView?, uri: Uri?, favicon: Bitmap?) -> Unit)? = null,
    pageFinished: ((view: WebView?, uri: Uri?) -> Unit)? = null,
    receivedError: ((view: WebView?, request: WebResourceRequest?, error: WebResourceError?) -> Unit)? = null
) {
    webViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            pageStarted?.let { callback -> callback(view, url?.toUri(), favicon) }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            pageFinished?.let { callback -> callback(view, url?.toUri()) }
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean =
            when (val result = overrideUrlLoading?.let { callback -> callback(view, request?.url) }) {
                null -> request?.url.let { uri ->
                    when (uri?.scheme) {
                        null -> false
                        "http" -> false
                        "tel" -> {
                            view?.context?.startActivity(Intent(Intent.ACTION_DIAL, uri))
                            true
                        }
                        "mailto" -> {
                            view?.context?.startActivity(Intent(Intent.ACTION_SENDTO, uri))
                            true
                        }
                        else -> false
                    }
                }
                else -> result
            }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            receivedError?.let { callback -> callback(view, request, error) }
        }
    }
}
