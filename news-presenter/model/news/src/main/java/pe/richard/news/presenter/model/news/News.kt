package pe.richard.news.presenter.model.news

import android.net.Uri

data class News(
    val id: String,
    val source: String,
    val title: String,
    val target: Uri,
    val image: Uri?,
    val author: String?,
    val description: String?,
    val content: String?,
    val publishedAt: Long,
    val openedAt: Long?
)
