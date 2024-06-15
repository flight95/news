package pe.richard.news.presenter.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pe.richard.library.domain.news.GetNews
import pe.richard.library.domain.news.RemoveNews
import pe.richard.library.domain.news.SetNews
import pe.richard.news.presenter.core.paging.PagingSourceImplement
import pe.richard.news.presenter.model.news.News
import pe.richard.news.presenter.model.news.toData
import pe.richard.news.presenter.model.news.toModel
import javax.inject.Inject

@HiltViewModel
internal class HomePresenterImplement @Inject constructor(
    application: Application,
    private val state: SavedStateHandle,
    private val setNews: SetNews,
    private val getNews: GetNews,
    private val removeNews: RemoveNews
) : AndroidViewModel(application),
    HomePresenter {

    override val newsFlow: Flow<PagingData<News>> =
        Pager(
            config = PagingConfig(
                pageSize = 16,
                initialLoadSize = 16
            ),
            initialKey = 1
        ) {
            PagingSourceImplement { page, size ->
                getNews(
                    page = page,
                    size = size
                ).map { paging -> paging.toModel { entity -> entity.toData() } }
            }
        }.flow.cachedIn(viewModelScope)

    override fun applyNews(
        news: News,
        callback: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            setNews(news.toModel())
                .catch { callback(false) }
                .collect { model -> callback(model != null) }
        }
    }

    override fun clearNews(
        callback: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            removeNews()
                .catch { callback(false) }
                .collect { model -> callback(model) }
        }
    }
}
