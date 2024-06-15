package pe.richard.library.domain.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pe.richard.library.data.news.NewsRepository
import pe.richard.library.domain.news.GetNews
import pe.richard.library.domain.news.GetNewsImplement

@Module
@InstallIn(ViewModelComponent::class)
internal object GetNewsModule {

    @Provides
    @ViewModelScoped
    fun provideGetNews(
        repository: NewsRepository
    ): GetNews = GetNewsImplement.getInstance(
        repository
    )
}
