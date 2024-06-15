package pe.richard.news.view.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import pe.richard.news.view.core.view.setOnApplyWindowInsetsListener
import pe.richard.news.view.core.web.initializeWebChromeClient
import pe.richard.news.view.core.web.initializeWebSettings
import pe.richard.news.view.core.web.initializeWebViewClient
import pe.richard.news.view.news.databinding.NewsActivityBinding

class NewsActivity :
    AppCompatActivity() {

    //region Intent, deeplink and new intent.

    private enum class IntentParameter(val value: String) {
        Uri("news_uri")
    }

    companion object {

        fun newIntent(
            context: Context?,
            uri: Uri
        ) = context?.let { activity ->
            Intent(activity, NewsActivity::class.java)
                .apply { putExtra(IntentParameter.Uri.value, uri.toString()) }
        }

        private val Intent.uriParameter: Uri?
            get() = getStringExtra(IntentParameter.Uri.value)?.toUri()
    }

    //endregion

    //region View binding and presenter.

    private var binding: NewsActivityBinding? = null

    //endregion

    //region Fragment lifecycle.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // https://developer.android.com/develop/ui/views/layout/edge-to-edge
        enableEdgeToEdge()
        NewsActivityBinding.inflate(layoutInflater)
            .apply {
                binding = this
                setContentView(root)
                root.setOnApplyWindowInsetsListener()
            }

        // https://developer.android.com/reference/androidx/activity/OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this) { finish() }

        // Parsing uri.
        when (val uri = savedInstanceState?.getString(IntentParameter.Uri.value)) {
            null -> intent.uriParameter
            else -> uri.toUri()
        }.let { uri ->
            when (uri) {
                null -> onBackPressedDispatcher.onBackPressed()
                else -> bindContents(uri) // Bind views.
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        intent.uriParameter?.let { uri -> outState.putString(IntentParameter.Uri.value, uri.toString()) }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    //endregion

    //region Bind views.

    private fun bindContents(uri: Uri) {
        binding?.newsContents?.apply {
            initializeWebSettings()
            initializeWebChromeClient()
            initializeWebViewClient()
            loadUrl(uri.toString())
        }
    }

    //endregion
}
