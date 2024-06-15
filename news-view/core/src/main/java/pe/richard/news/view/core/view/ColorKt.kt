package pe.richard.news.view.core.view

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.getColor(@ColorRes id: Int) =
    context?.let { activity -> ContextCompat.getColor(activity, id) }
