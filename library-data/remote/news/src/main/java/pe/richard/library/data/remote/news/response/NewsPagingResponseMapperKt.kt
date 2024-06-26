package pe.richard.library.data.remote.news.response

import pe.richard.library.domain.model.core.date.UtcFormat
import pe.richard.library.domain.model.core.primitive.toUri
import pe.richard.library.domain.model.core.primitive.trimOrNull
import pe.richard.library.domain.model.news.NewsModel

internal fun NewsPagingResponse.NewsResponse.toModel() =
    try {
        NewsModel(
            source = source?.name?.trimOrNull() ?: throw IllegalStateException("News source could not be empty."),
            title = title?.trimOrNull() ?: throw IllegalStateException("News title could not be empty."),
            target = url?.toUri() ?: throw IllegalStateException("News target could not be empty."),
            image = urlToImage?.toUri(),
            author = author?.trimOrNull(),
            description = description?.trimOrNull(),
            content = content?.trimOrNull(),
            publishedAt = try {
                UtcFormat.parse(publishedAt).time
            } catch (e: Throwable) {
                throw IllegalStateException("News published at could not be empty.")
            },
            openedAt = null
        )
    } catch (e: Throwable) {
        null
    }
