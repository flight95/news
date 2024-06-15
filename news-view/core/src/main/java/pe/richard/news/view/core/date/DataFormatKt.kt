package pe.richard.news.view.core.date

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("ConstantLocale")
val TimeFormat =
    SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
