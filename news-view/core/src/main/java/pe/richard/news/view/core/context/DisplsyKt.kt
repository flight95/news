package pe.richard.news.view.core.context

import android.content.Context
import kotlin.math.round

val Context.screenWidth: Int?
    get() = resources?.displayMetrics?.widthPixels

fun Int.toPixels(context: Context?): Int? =
    context?.resources?.displayMetrics?.density
        ?.let { density -> round(toFloat() * density).toInt() }

val Context.tablet: Boolean?
    get() = screenWidth?.let { screen -> screen >= (600.toPixels(this) ?: 0) }
