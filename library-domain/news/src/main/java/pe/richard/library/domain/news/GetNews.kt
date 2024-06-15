package pe.richard.library.domain.news

import kotlinx.coroutines.flow.Flow
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.news.NewsModel

interface GetNews {

    operator fun invoke(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>>
}
