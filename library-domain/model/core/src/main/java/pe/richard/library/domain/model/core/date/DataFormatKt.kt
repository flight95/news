package pe.richard.library.domain.model.core.date

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import java.util.Locale

val UtcFormat =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK)
        .apply { timeZone = TimeZone.getTimeZone("UTC") }
