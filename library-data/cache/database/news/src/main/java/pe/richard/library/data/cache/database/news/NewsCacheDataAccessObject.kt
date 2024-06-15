package pe.richard.library.data.cache.database.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.richard.library.data.cache.database.news.entity.NewsEntity

@Dao
interface NewsCacheDataAccessObject {

    @Query(
        """
            SELECT count(news_id) FROM NewsEntities
        """
    )
    suspend fun count(): Long

    @Query(
        """
            SELECT * FROM NewsEntities
            ORDER BY news_published_at
            LIMIT :limit OFFSET :offset
        """
    )
    suspend fun select(
        offset: Int,
        limit: Int
    ): List<NewsEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<NewsEntity>)

    @Query(
        """
            DELETE FROM NewsEntities
        """
    )
    suspend fun delete()
}
