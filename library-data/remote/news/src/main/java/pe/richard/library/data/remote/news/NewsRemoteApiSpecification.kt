package pe.richard.library.data.remote.news

import pe.richard.library.data.remote.news.response.NewsPagingResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsRemoteApiSpecification : NewsRemoteApi {

    @GET("/v2/top-headlines?country=kr")
    override suspend fun get(
        @Query("apiKey") authorization: String,
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): NewsPagingResponse
}
