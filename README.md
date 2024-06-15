# News

![Static Badge](https://img.shields.io/badge/Clean%20Architecture-purple)  
![Static Badge](https://img.shields.io/badge/Kotlin-2.0.0-red)
![Static Badge](https://img.shields.io/badge/Kotlin%20Coroutines-1.8.0-red)
![Static Badge](https://img.shields.io/badge/Kotlin%20Serialization-1.6.3-red)
![Static Badge](https://img.shields.io/badge/Ktlint-1.3.0-red)
![Static Badge](https://img.shields.io/badge/Dagger%20Hilt-2.50-red)
![Static Badge](https://img.shields.io/badge/Paging-3.3.0-red)
![Static Badge](https://img.shields.io/badge/OkHttp-4.12.0-red)
![Static Badge](https://img.shields.io/badge/Retrofit-2.9.0-red)
![Static Badge](https://img.shields.io/badge/Glide-4.15.1-red)

- https://newsapi.org
- Search worldwide news with code.
  Locate articles and breaking news headlines from news sources and blogs across the web with our JSON API.

## Clean Architecture Modules

![Static Badge](https://img.shields.io/badge/application-grey)
![Static Badge](https://img.shields.io/badge/view-home-red)
![Static Badge](https://img.shields.io/badge/view-core-purple)
![Static Badge](https://img.shields.io/badge/presenter-home-red)
![Static Badge](https://img.shields.io/badge/presenter-model.news-darkred)
![Static Badge](https://img.shields.io/badge/presenter-core-purple)
![Static Badge](https://img.shields.io/badge/domain-news-red)
![Static Badge](https://img.shields.io/badge/domain-model.news-darkred)
![Static Badge](https://img.shields.io/badge/domain-model.core-purple)
![Static Badge](https://img.shields.io/badge/data-news-red)
![Static Badge](https://img.shields.io/badge/data.remote-news-red)
![Static Badge](https://img.shields.io/badge/data.remote-core-purple)

### Dependencies

![Static Badge](https://img.shields.io/badge/view-home-red) &rarr;
![Static Badge](https://img.shields.io/badge/presenter-home-red) &rarr;
![Static Badge](https://img.shields.io/badge/domain-news-red)
![Static Badge](https://img.shields.io/badge/data.remote-news-red) &rarr;
![Static Badge](https://img.shields.io/badge/data-news-red) &rarr;
![Static Badge](https://img.shields.io/badge/domain-news-red)

## Glide and Image cache

- Use [signature](https://bumptech.github.io/glide/doc/caching.html#cache-keys) for cache key.

### ApplicationGlideModule

- Use [DiskCache](https://bumptech.github.io/glide/doc/configuration.html#disk-cache) max size:
  [InternalCacheDiskCacheFactory(context, Long.MAX_VALUE)](library/src/main/java/com/bumptech/glide/load/engine/cache/InternalCacheDiskCacheFactory.java)
- Skip memory cache.
- This will force the image URL to change from HTTP to HTTPS,
  but depending on the image server, an SSLHandshakeException may be thrown.
