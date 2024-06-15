package pe.richard.library.data.cache.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pe.richard.library.data.cache.database.Database
import pe.richard.library.data.cache.news.entity.toEntity
import pe.richard.library.data.cache.news.entity.toModel
import pe.richard.library.domain.model.core.paging.PagingModel
import pe.richard.library.domain.model.news.NewsModel

internal class NewsCacheDataSourceImplement private constructor(
    private val database: Database
) : NewsCacheDataSource {

    companion object {
        fun getInstance(
            database: Database
        ): NewsCacheDataSource = NewsCacheDataSourceImplement(
            database
        )
    }

    private val dao = database.getNewsCacheDataAccessObject()

    override fun set(
        model: NewsModel
    ): Flow<NewsModel?> =
        flow { emit(model.toEntity()) }
            .map { entity ->
                entity?.let { fixed ->
                    dao.insert(fixed)
                    model
                }
            }
            .flowOn(Dispatchers.IO)

    override fun get(
        page: Int,
        size: Int
    ): Flow<PagingModel<NewsModel>> =
        flow { emit(dao.select((page - 1) * size, size)) }
            .combine(flow { emit(dao.count()) }) { entities, total ->
                PagingModel(
                    total = total,
                    end = total <= (page * size),
                    contents = entities?.mapNotNull { entity -> entity.toModel() } ?: listOf()
                )
            }
            .flowOn(Dispatchers.IO)

    override fun add(
        models: List<NewsModel>
    ): Flow<List<NewsModel>> =
        flow { emit(models.mapNotNull { model -> model.toEntity() }) }
            .map { entities ->
                if (entities.isNotEmpty()) dao.insert(entities)
                models
            }
            .flowOn(Dispatchers.IO)

    override fun remove(): Flow<Boolean> =
        flow { emit(dao.delete()) }
            .map { true }
            .flowOn(Dispatchers.IO)
}
