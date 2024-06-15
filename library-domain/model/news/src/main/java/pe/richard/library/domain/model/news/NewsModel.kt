package pe.richard.library.domain.model.news

import android.net.Uri
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

data class NewsModel(
    val source: String,
    val title: String,
    val target: Uri,
    val image: Uri?,
    val author: String?,
    val description: String?,
    val content: String?,
    val publishedAt: Long,
    val openedAt: Long?
) {

    @OptIn(ExperimentalEncodingApi::class)
    val id: String = Base64.encode("$source$title$target$publishedAt".toByteArray())
}
