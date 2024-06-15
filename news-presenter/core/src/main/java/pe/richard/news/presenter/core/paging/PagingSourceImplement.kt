package pe.richard.news.presenter.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import pe.richard.library.domain.model.core.paging.PagingModel

class PagingSourceImplement<T : Any>(
    private val getContents: (page: Int, size: Int) -> Flow<PagingModel<T>>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? =
        state.anchorPosition
            ?.let { anchorPosition -> state.closestPageToPosition(anchorPosition) }
            ?.let { anchorPage -> anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1) }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
        try {
            val page = params.key ?: 1
            val size = params.loadSize
            when (val paging = getContents(page, size).firstOrNull()) {
                null -> LoadResult.Invalid()
                else -> {
                    LoadResult.Page(
                        data = paging.contents,
                        prevKey = when (page > 1) {
                            true -> page - 1
                            else -> null
                        },
                        nextKey = when (paging.end) {
                            true -> null
                            else -> page + 1
                        }
                    )
                }
            }
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
}
