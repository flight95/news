package pe.richard.news.view.core.paging

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.refresh() {
    adapter?.let { fixed ->
        when (fixed) {
            is PagingDataAdapter<*, *> -> fixed.refresh()
            is ConcatAdapter -> {
                fixed.adapters.filterIsInstance<PagingDataAdapter<*, *>>()
                    .firstOrNull()
                    ?.refresh()
            }
            else -> Unit
        }
    }
}
