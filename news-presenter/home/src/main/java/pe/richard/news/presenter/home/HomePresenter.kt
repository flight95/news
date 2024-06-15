package pe.richard.news.presenter.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pe.richard.news.presenter.model.news.News

interface HomePresenter {

    val newsFlow: Flow<PagingData<News>>
    fun clearNews(callback: (Boolean) -> Unit)
}
