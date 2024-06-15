package pe.richard.library.domain.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pe.richard.library.data.news.NewsRepository
import pe.richard.library.domain.news.RemoveNews
import pe.richard.library.domain.news.RemoveNewsImplement

@Module
@InstallIn(ViewModelComponent::class)
internal object RemoveNewsModule {

    @Provides
    @ViewModelScoped
    fun provideRemoveNews(
        repository: NewsRepository
    ): RemoveNews = RemoveNewsImplement.getInstance(
        repository
    )
}
