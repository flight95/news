package pe.richard.news.view.core.paging

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.setRefresh(view: RecyclerView?) {
    setOnRefreshListener {
        when (val adapter = view?.adapter) {
            is PagingDataAdapter<*, *> -> adapter.refresh()
            is ConcatAdapter -> {
                adapter.adapters.filterIsInstance<PagingDataAdapter<*, *>>()
                    .firstOrNull()
                    ?.refresh()
            }
            else -> Unit
        }
        isRefreshing = false
    }
}
