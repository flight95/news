package pe.richard.library.data.remote.news.response

import kotlinx.serialization.Serializable

@Serializable
internal data class NewsPagingResponse(
    val totalResults: Int? = null,
    val articles: List<NewsResponse>? = null
) {

    @Serializable
    data class NewsResponse(
        val source: SourceResponse? = null,
        val author: String? = null,
        val title: String? = null,
        val description: String? = null,
        val content: String? = null,
        val url: String? = null,
        val urlToImage: String? = null,
        val publishedAt: String? = null
    ) {

        @Serializable
        data class SourceResponse(
            val id: String? = null,
            val name: String? = null
        )
    }
}
