package pe.richard.library.data.news

import kotlinx.coroutines.flow.Flow
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.news.NewsModel

interface NewsRepository {

    fun get(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>>

    fun remove(): Flow<Boolean>
}
