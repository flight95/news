package pe.richard.library.domain.model.core.paging

data class PagingModel<T>(
    val total: Long = 0L,
    val end: Boolean = true,
    val contents: List<T> = listOf()
) {

    fun <R> toModel(transform: (T) -> R) =
        PagingModel(
            total = total,
            end = end,
            contents = contents.map { content -> transform(content) }
        )
}
