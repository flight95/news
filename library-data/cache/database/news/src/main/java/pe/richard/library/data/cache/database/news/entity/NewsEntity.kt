package pe.richard.library.data.cache.database.news.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NewsEntities")
data class NewsEntity(
    @PrimaryKey @ColumnInfo(name = "news_id") val id: String = "",
    @ColumnInfo(name = "news_source") val source: String = "",
    @ColumnInfo(name = "news_title") val title: String = "",
    @ColumnInfo(name = "news_target") val target: String = "",
    @ColumnInfo(name = "news_image") val image: String = "",
    @ColumnInfo(name = "news_author") val author: String = "",
    @ColumnInfo(name = "news_description") val description: String = "",
    @ColumnInfo(name = "news_content") val content: String = "",
    @ColumnInfo(name = "news_published_at") val publishedAt: Long = -1L,
    @ColumnInfo(name = "news_opened_at") val openedAt: Long = -1L
) {
    companion object {
        const val CREATOR =
            """
                CREATE TABLE IF NOT EXISTS NewsEntities (
                    news_id TEXT PRIMARY KEY NOT NULL,
                    news_source TEXT NOT NULL DEFAULT '',
                    news_title TEXT NOT NULL DEFAULT '',
                    news_target TEXT NOT NULL DEFAULT '',
                    news_image TEXT NOT NULL DEFAULT '',
                    news_author TEXT NOT NULL DEFAULT '',
                    news_description TEXT NOT NULL DEFAULT '',
                    news_content TEXT NOT NULL DEFAULT '',
                    news_published_at INTEGER NOT NULL DEFAULT(-1),
                    news_opened_at INTEGER NOT NULL DEFAULT(-1)
                )
            """
    }
}
