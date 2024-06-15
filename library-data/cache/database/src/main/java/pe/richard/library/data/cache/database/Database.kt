package pe.richard.library.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.richard.library.data.cache.database.news.NewsCacheDataAccessObject
import pe.richard.library.data.cache.database.news.entity.NewsEntity

@Database(
    entities = [
        NewsEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun getNewsCacheDataAccessObject(): NewsCacheDataAccessObject
}
