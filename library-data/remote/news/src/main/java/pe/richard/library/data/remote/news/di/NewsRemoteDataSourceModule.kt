package pe.richard.library.data.remote.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pe.richard.library.data.remote.news.NewsRemoteApi
import pe.richard.library.data.remote.news.NewsRemoteDataSource
import pe.richard.library.data.remote.news.NewsRemoteDataSourceImplement

@Module
@InstallIn(ViewModelComponent::class)
internal object NewsRemoteDataSourceModule {

    @Provides
    @ViewModelScoped
    fun provideNewsRemoteDataSource(
        api: NewsRemoteApi
    ): NewsRemoteDataSource = NewsRemoteDataSourceImplement.getInstance(
        api
    )
}
