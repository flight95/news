package pe.richard.library.entity.core.primitive

fun Int.aboveOrNull() =
    when (this < 0) {
        true -> null
        else -> this
    }
