package pe.richard.library.domain.news

import kotlinx.coroutines.flow.Flow
import pe.richard.library.domain.model.news.NewsModel

interface SetNews {

    operator fun invoke(
        model: NewsModel
    ): Flow<NewsModel?>
}
