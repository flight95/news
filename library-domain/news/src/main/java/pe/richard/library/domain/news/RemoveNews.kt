package pe.richard.library.domain.news

import kotlinx.coroutines.flow.Flow

interface RemoveNews {
    operator fun invoke(): Flow<Boolean>
}
