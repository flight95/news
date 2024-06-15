package pe.richard.library.data.cache.news

import kotlinx.coroutines.flow.Flow
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.news.NewsModel

interface NewsCacheDataSource {
    fun get(): Flow<PagingModel<NewsModel>>
}
