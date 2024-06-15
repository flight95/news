package pe.richard.library.domain.model.core.primitive

fun Int.aboveOrNull() =
    when (this < 0) {
        true -> null
        else -> this
    }

fun Long.aboveOrNull() =
    when (this < 0L) {
        true -> null
        else -> this
    }
