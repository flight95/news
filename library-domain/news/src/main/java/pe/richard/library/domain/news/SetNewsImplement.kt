package pe.richard.library.domain.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import pe.richard.library.data.news.NewsRepository
import pe.richard.library.domain.model.news.NewsModel

internal class SetNewsImplement private constructor(
    private val repository: NewsRepository
) : SetNews {

    companion object {
        fun getInstance(
            repository: NewsRepository
        ): SetNews = SetNewsImplement(
            repository
        )
    }

    override fun invoke(
        model: NewsModel
    ): Flow<NewsModel?> =
        repository.set(model)
            .flowOn(Dispatchers.Default)
}
