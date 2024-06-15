package pe.richard.library.data.cache.news

import kotlinx.coroutines.flow.Flow
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.news.NewsModel

interface NewsCacheDataSource {

    fun set(
        model: NewsModel
    ): Flow<NewsModel?>

    fun get(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>>

    fun add(
        models: List<NewsModel>
    ): Flow<List<NewsModel>>

    fun remove(): Flow<Boolean>
}
