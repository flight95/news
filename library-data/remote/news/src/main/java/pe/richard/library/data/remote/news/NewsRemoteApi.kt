package pe.richard.library.data.remote.news

import pe.richard.library.data.remote.news.response.NewsPagingResponse

internal interface NewsRemoteApi {

    suspend fun get(
        authorization: String,
        page: Int,
        size: Int
    ): NewsPagingResponse
}
