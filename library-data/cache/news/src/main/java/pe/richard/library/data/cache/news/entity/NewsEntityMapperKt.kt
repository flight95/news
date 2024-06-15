package pe.richard.library.data.cache.news.entity

import pe.richard.library.data.cache.database.news.entity.NewsEntity
import pe.richard.library.domain.model.core.primitive.aboveOrNull
import pe.richard.library.domain.model.core.primitive.toUri
import pe.richard.library.domain.model.news.NewsModel
import pe.richard.library.entity.core.primitive.trimOrNull

fun NewsEntity.toModel() =
    try {
        NewsModel(
            source = source.trimOrNull() ?: throw IllegalStateException("News source could not be empty."),
            title = title.trimOrNull() ?: throw IllegalStateException("News title could not be empty."),
            target = target.toUri() ?: throw IllegalStateException("News target could not be empty."),
            image = image.toUri(),
            author = author.trimOrNull(),
            description = description.trimOrNull(),
            content = content.trimOrNull(),
            publishedAt = publishedAt.aboveOrNull() ?: throw IllegalStateException("News published at could not be empty."),
            openedAt = openedAt.aboveOrNull()
        )
    } catch (e: Throwable) {
        null
    }

fun NewsModel.toEntity() =
    try {
        NewsEntity(
            id = id.trimOrNull() ?: throw IllegalStateException("News id could not be empty."),
            source = source.trimOrNull() ?: throw IllegalStateException("News source could not be empty."),
            title = title.trimOrNull() ?: throw IllegalStateException("News title could not be empty."),
            target = target.toString(),
            image = image?.toString() ?: "",
            author = author?.trimOrNull() ?: "",
            description = description?.trimOrNull() ?: "",
            content = content?.trimOrNull() ?: "",
            publishedAt = publishedAt.aboveOrNull() ?: throw IllegalStateException("News published at could not be empty."),
            openedAt = openedAt?.aboveOrNull() ?: -1L
        )
    } catch (e: Throwable) {
        null
    }
