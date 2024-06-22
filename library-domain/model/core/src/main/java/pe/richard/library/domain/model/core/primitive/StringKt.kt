package pe.richard.library.domain.model.core.primitive

fun String.trimOrNull() =
    trim().let { fixed ->
        when (fixed.isEmpty()) {
            true -> null
            else -> fixed
        }
    }
