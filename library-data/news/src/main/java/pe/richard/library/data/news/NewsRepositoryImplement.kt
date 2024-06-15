package pe.richard.library.data.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import pe.richard.library.data.cache.news.NewsCacheDataSource
import pe.richard.library.data.remote.news.NewsRemoteDataSource
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.core.paging.flotMapEmptyContents
import pe.richard.library.domain.model.news.NewsModel

internal class NewsRepositoryImplement private constructor(
    private val cache: NewsCacheDataSource,
    private val remote: NewsRemoteDataSource
) : NewsRepository {

    companion object {
        fun getInstance(
            cache: NewsCacheDataSource,
            remote: NewsRemoteDataSource
        ): NewsRepository = NewsRepositoryImplement(
            cache,
            remote
        )
    }

    override fun set(
        model: NewsModel
    ): Flow<NewsModel?> =
        cache.set(model)
            .flowOn(Dispatchers.Default)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun get(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>> =
        cache.get(page, size)
            .flotMapEmptyContents {
                remote.get(page, size)
                    .flatMapConcat { response -> cache.add(response.contents) }
                    .flatMapConcat { cache.get(page, size) }
            }
            .flowOn(Dispatchers.Default)

    override fun remove(): Flow<Boolean> =
        cache.remove()
            .flowOn(Dispatchers.Default)
}
