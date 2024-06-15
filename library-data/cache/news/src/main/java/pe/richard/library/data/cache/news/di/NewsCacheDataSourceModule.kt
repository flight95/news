package pe.richard.library.data.cache.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pe.richard.library.data.cache.database.Database
import pe.richard.library.data.cache.news.NewsCacheDataSource
import pe.richard.library.data.cache.news.NewsCacheDataSourceImplement

@Module
@InstallIn(ViewModelComponent::class)
internal class NewsCacheDataSourceModule {

    @Provides
    @ViewModelScoped
    fun provideSearchCacheDataSource(
        database: Database
    ): NewsCacheDataSource = NewsCacheDataSourceImplement.getInstance(
        database
    )
}
