package pe.richard.library.data.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pe.richard.library.data.news.NewsRepository
import pe.richard.library.data.news.NewsRepositoryImplement
import pe.richard.library.data.remote.news.NewsRemoteDataSource

@Module
@InstallIn(ViewModelComponent::class)
internal object NewsRepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideNewsRepository(
        remote: NewsRemoteDataSource
    ): NewsRepository = NewsRepositoryImplement.getInstance(
        remote
    )
}
