package pe.richard.library.data.remote.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pe.richard.library.data.remote.news.NewsRemoteApi
import pe.richard.library.data.remote.news.NewsRemoteApiImplement
import pe.richard.library.data.remote.news.NewsRemoteApiSpecification
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
internal class NewsRemoteApiModule {

    @Provides
    @ViewModelScoped
    fun provideNewsRemoteApi(
        @Named("news-server") server: String,
        builder: Retrofit.Builder
    ): NewsRemoteApi =
        NewsRemoteApiImplement.getInstance(
            builder.baseUrl(server)
                .build()
                .create(NewsRemoteApiSpecification::class.java)
        )
}
