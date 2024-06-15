package pe.richard.library.domain.model.core.paging

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<PagingModel<T>>.flotMapEmptyContents(
    callback: () -> Flow<PagingModel<T>>
): Flow<PagingModel<T>> =
    this.flatMapConcat { paging ->
        when (paging.contents.isEmpty()) {
            true -> callback()
            false -> flow { emit(paging) }
        }
    }.flowOn(Dispatchers.Default)
