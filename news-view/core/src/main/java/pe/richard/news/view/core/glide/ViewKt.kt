package pe.richard.news.view.core.glide

import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey

fun AppCompatImageView.loadImage(
    key: String,
    uri: Uri?
) = context?.let { owner ->
    setImageDrawable(null)
    uri?.let { source ->
        Glide.with(owner)
            .load(source)
            .signature(ObjectKey(key))
            .into(this)
    }
}
