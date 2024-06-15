package pe.richard.news.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pe.richard.news.presenter.home.HomePresenter
import pe.richard.news.presenter.home.di.homePresenter
import pe.richard.news.presenter.model.news.News
import pe.richard.news.view.core.context.tablet
import pe.richard.news.view.core.date.TimeFormat
import pe.richard.news.view.core.glide.loadImage
import pe.richard.news.view.core.paging.refresh
import pe.richard.news.view.core.view.clicks
import pe.richard.news.view.core.view.setOnApplyWindowInsetsListener
import pe.richard.news.view.home.databinding.HomeActivityBinding
import pe.richard.news.view.home.databinding.HomeNewsItemBinding
import pe.richard.news.view.home.databinding.HomeNewsItemStateBinding
import pe.richard.news.view.news.NewsActivity
import java.util.Date

@AndroidEntryPoint
class HomeActivity :
    AppCompatActivity() {

    //region View binding and presenter.

    private var binding: HomeActivityBinding? = null

    private val presenter: HomePresenter by homePresenter()

    //endregion

    //region Fragment lifecycle.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // https://developer.android.com/develop/ui/views/layout/edge-to-edge
        enableEdgeToEdge()
        HomeActivityBinding.inflate(layoutInflater)
            .apply {
                binding = this
                setContentView(root)
                root.setOnApplyWindowInsetsListener()
            }

        // https://developer.android.com/reference/androidx/activity/OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this) { finish() }

        // Bind views.
        bindActionBar()
        bindRefresh()
        bindContents()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    //endregion

    //region Bind views.

    private fun bindActionBar() {
        binding?.apply {
            setSupportActionBar(homeToolbar)
            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(false)
                setTitle(R.string.home_title)
            }
        }
    }

    private fun bindRefresh() {
        binding?.homeRefresh?.let { view ->
            view.setOnRefreshListener {
                presenter.clearNews {
                    binding?.homeContents?.refresh()
                    view.isRefreshing = false
                }
            }
        }
    }

    private fun bindContents() {
        Adapter(
            onClicked = { news ->
                NewsActivity.newIntent(this, news.target)
                    ?.let { intent -> startActivity(intent) }
            }
        ).let { adapter ->
            binding?.homeContents?.let { view ->
                view.adapter = adapter.withLoadStateFooter(StateAdapter { adapter.retry() })
                view.layoutManager = GridLayoutManager(
                    this,
                    when (tablet) {
                        true -> 3
                        else -> 1
                    }
                )
            }
            lifecycleScope.launch {
                presenter.newsFlow.collectLatest { paging -> adapter.submitData(paging) }
            }
        }
    }

    //endregion

    //region Adapter, view holders and model of adapter.

    private class Adapter(
        private val onClicked: (News) -> Unit
    ) : PagingDataAdapter<News, ViewHolder>(
        object : DiffUtil.ItemCallback<News>() {

            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
                areItemsTheSame(oldItem, newItem) &&
                    oldItem.openedAt == newItem.openedAt
        }
    ) {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ) = ViewHolder(
            HomeNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClicked
        )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            getItem(position)?.let { news -> holder.bind(news) }
        }
    }

    private class ViewHolder(
        binding: HomeNewsItemBinding,
        private val onClicked: (News) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val imageView = binding.newsImage
        private val sourceView = binding.newsSource
        private val titleView = binding.newsTitle
        private val publishedView = binding.newsPublished
        private val actionView = binding.newsAction

        fun bind(news: News) {
            imageView.loadImage(news.id, news.image)
            sourceView.text = news.source
            titleView.text = news.title
            publishedView.text = TimeFormat.format(Date(news.publishedAt))
            actionView.clicks { onClicked(news) }
        }
    }

    private class StateAdapter(
        private val onRetry: () -> Unit
    ) : LoadStateAdapter<StateViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
        ) = StateViewHolder(
            HomeNewsItemStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onRetry
        )

        override fun onBindViewHolder(
            holder: StateViewHolder,
            loadState: LoadState
        ) {
            holder.bind(loadState)
        }
    }

    private class StateViewHolder(
        binding: HomeNewsItemStateBinding,
        private val onRetry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val progressView = binding.newsItemProgress
        private val retryView = binding.newsItemRetry

        fun bind(state: LoadState) {
            when (state) {
                is LoadState.Loading -> {
                    progressView.visibility = View.VISIBLE
                    retryView.visibility = View.GONE
                }
                is LoadState.Error -> {
                    progressView.visibility = View.GONE
                    retryView.visibility = View.VISIBLE
                }
                else -> {
                    progressView.visibility = View.GONE
                    retryView.visibility = View.GONE
                }
            }
            retryView.clicks {
                when (state) {
                    is LoadState.Error -> onRetry()
                    else -> Unit
                }
            }
        }
    }

    //endregion
}
