package pe.richard.library.domain.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import pe.richard.library.data.news.NewsRepository

internal class RemoveNewsImplement private constructor(
    private val repository: NewsRepository
) : RemoveNews {

    companion object {
        fun getInstance(
            repository: NewsRepository
        ): RemoveNews = RemoveNewsImplement(
            repository
        )
    }

    override fun invoke(): Flow<Boolean> =
        repository.remove()
            .flowOn(Dispatchers.Default)
}
