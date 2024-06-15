package pe.richard.library.domain.model.core.primitive

import android.net.Uri

fun String.toUri() =
    try {
        Uri.parse(this)
    } catch (e: Throwable) {
        null
    }
