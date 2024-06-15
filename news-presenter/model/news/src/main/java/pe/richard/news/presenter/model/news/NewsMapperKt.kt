package pe.richard.news.presenter.model.news

import pe.richard.library.domain.model.news.NewsModel

fun NewsModel.toData() =
    News(
        id = id,
        source = source,
        title = title,
        target = target,
        image = image,
        author = author,
        description = description,
        content = content,
        publishedAt = publishedAt,
        openedAt = openedAt
    )

fun News.toModel() =
    NewsModel(
        source = source,
        title = title,
        target = target,
        image = image,
        author = author,
        description = description,
        content = content,
        publishedAt = publishedAt,
        openedAt = openedAt
    )
