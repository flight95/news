package pe.richard.library.data.cache.database.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.richard.library.data.cache.database.Database
import pe.richard.library.data.cache.database.migration.RoomVersion2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): Database =
        Room.databaseBuilder(application, Database::class.java, "richard-library-data-cache-database")
            .addMigrations(
                RoomVersion2()
            )
            .build()
}
