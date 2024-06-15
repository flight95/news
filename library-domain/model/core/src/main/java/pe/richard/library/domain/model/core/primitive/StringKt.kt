package pe.richard.library.entity.core.primitive

fun String.trimOrNull() =
    trim().let { fixed ->
        when (fixed.isEmpty()) {
            true -> null
            else -> fixed
        }
    }
