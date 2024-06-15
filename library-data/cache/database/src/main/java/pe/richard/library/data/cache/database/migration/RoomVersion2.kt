package pe.richard.library.data.cache.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pe.richard.library.data.cache.database.news.entity.NewsEntity

internal class RoomVersion2 : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(NewsEntity.CREATOR.trimIndent())
    }
}
