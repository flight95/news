package pe.richard.library.data.remote.news

import kotlinx.coroutines.flow.Flow
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.news.NewsModel

interface NewsRemoteDataSource {

    fun get(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>>
}
