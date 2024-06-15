package pe.richard.library.domain.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pe.richard.library.data.news.NewsRepository
import pe.richard.library.domain.news.SetNews
import pe.richard.library.domain.news.SetNewsImplement

@Module
@InstallIn(ViewModelComponent::class)
internal object SetNewsModule {

    @Provides
    @ViewModelScoped
    fun provideSetNews(
        repository: NewsRepository
    ): SetNews = SetNewsImplement.getInstance(
        repository
    )
}
