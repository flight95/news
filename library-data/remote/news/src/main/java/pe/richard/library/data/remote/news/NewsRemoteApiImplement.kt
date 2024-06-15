package pe.richard.library.data.remote.news

internal class NewsRemoteApiImplement private constructor(private val specification: NewsRemoteApiSpecification) : NewsRemoteApi {

    companion object {
        fun getInstance(
            specification: NewsRemoteApiSpecification
        ): NewsRemoteApi = NewsRemoteApiImplement(
            specification
        )
    }

    override suspend fun get(
        authorization: String,
        page: Int,
        size: Int
    ) = specification.get(authorization, page, size)
}
