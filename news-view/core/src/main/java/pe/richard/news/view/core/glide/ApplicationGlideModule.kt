package pe.richard.news.view.core.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.richard.news.view.core.BuildConfig
import java.io.InputStream
import java.util.concurrent.TimeUnit

@GlideModule
class ApplicationGlideModule : AppGlideModule() {

    override fun applyOptions(
        context: Context,
        builder: GlideBuilder
    ) {
        super.applyOptions(context, builder)
        builder
            .setDiskCache(InternalCacheDiskCacheFactory(context, Long.MAX_VALUE))
            .setDefaultRequestOptions(
                RequestOptions()
                    .override(Target.SIZE_ORIGINAL)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
    }

    override fun registerComponents(
        context: Context,
        glide: Glide,
        registry: Registry
    ) {
        super.registerComponents(context, glide, registry)
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(
                provideOkHttpClient(
                    provideHttpsInterceptor(),
                    provideHttpLoggingInterceptor()
                )
            )
        )
    }

    private fun provideOkHttpClient(
        httpsInterceptor: Interceptor,
        loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpsInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    private fun provideHttpsInterceptor(): Interceptor =
        Interceptor { chain ->
            chain.run {
                request().let { request ->
                    val source = request.url.toString()
                    when (source.startsWith("http://")) {
                        true -> {
                            source.replaceFirst("http://", "https://")
                                .let { fixed ->
                                    request.newBuilder()
                                        .url(fixed)
                                        .build()
                                }
                        }
                        false -> request
                    }.let { changed -> proceed(changed) }
                }
            }
        }

    private fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BASIC
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
}
