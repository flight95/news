package pe.richard.news.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pe.richard.news.presenter.home.HomePresenter
import pe.richard.news.presenter.home.di.homePresenter
import pe.richard.news.presenter.model.news.News
import pe.richard.news.view.core.date.TimeFormat
import pe.richard.news.view.core.glide.loadImage
import pe.richard.news.view.core.view.setOnApplyWindowInsetsListener
import pe.richard.news.view.home.databinding.HomeActivityBinding
import pe.richard.news.view.home.databinding.HomeNewsItemBinding
import java.util.Date

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    //region View binding.

    private var binding: HomeActivityBinding? = null

    private val presenter: HomePresenter by homePresenter()

    //endregion

    //region Properties.

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

    private fun bindContents() {
        Adapter().let { adapter ->
            binding?.homeContents?.adapter = adapter
            lifecycleScope.launch {
                presenter.newsFlow.collectLatest { paging -> adapter.submitData(paging) }
            }
        }
    }

    //endregion

    //region Adapter, view holders and model of adapter.

    private class Adapter : PagingDataAdapter<News, ViewHolder>(
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
        ) = ViewHolder(HomeNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            getItem(position)?.let { news -> holder.bind(news) }
        }
    }

    private class ViewHolder(
        binding: HomeNewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val imageView = binding.newsImage
        private val sourceView = binding.newsSource
        private val titleView = binding.newsTitle
        private val publishedView = binding.newsPublished

        fun bind(news: News) {
            imageView.loadImage(news.id, news.image)
            sourceView.text = news.source
            titleView.text = news.title
            publishedView.text = TimeFormat.format(Date(news.publishedAt))
        }
    }

    //endregion
}
