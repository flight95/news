package pe.richard.library.data.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import pe.richard.library.data.remote.news.NewsRemoteDataSource
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.news.NewsModel

internal class NewsRepositoryImplement private constructor(private val remote: NewsRemoteDataSource) : NewsRepository {

    companion object {
        fun getInstance(
            remote: NewsRemoteDataSource
        ): NewsRepository = NewsRepositoryImplement(
            remote
        )
    }

    override fun get(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>> =
        remote.get(page, size)
            .flowOn(Dispatchers.Default)
}
