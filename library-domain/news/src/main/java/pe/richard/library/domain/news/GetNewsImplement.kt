package pe.richard.library.domain.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import pe.richard.library.data.news.NewsRepository
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.news.NewsModel

internal class GetNewsImplement private constructor(
    private val repository: NewsRepository
) : GetNews {

    companion object {
        fun getInstance(
            repository: NewsRepository
        ): GetNews = GetNewsImplement(
            repository
        )
    }

    override fun invoke(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>> =
        repository.get(page, size)
            .flowOn(Dispatchers.Default)
}
