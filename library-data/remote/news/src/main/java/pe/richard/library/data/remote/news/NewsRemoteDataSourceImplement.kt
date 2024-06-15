package pe.richard.library.data.remote.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pe.richard.library.data.remote.news.response.toModel
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.core.primitive.aboveOrNull
import pe.richard.library.domain.model.news.NewsModel

internal class NewsRemoteDataSourceImplement private constructor(
    private val api: NewsRemoteApi
) : NewsRemoteDataSource {

    companion object {
        fun getInstance(
            api: NewsRemoteApi
        ): NewsRemoteDataSource = NewsRemoteDataSourceImplement(
            api
        )
    }

    override fun get(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>> =
        flow { emit(api.get(BuildConfig.NEWS_API_KEY, page, size)) }
            .map { response ->
                val total = response.totalResults?.aboveOrNull() ?: throw IllegalStateException("News total could not be negative number.")
                PagingModel(
                    total = total.toLong(),
                    end = total <= (page * size),
                    contents = response.articles?.mapNotNull { content -> content.toModel() } ?: listOf()
                )
            }
            .flowOn(Dispatchers.IO)
}
