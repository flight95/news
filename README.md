# News

![Static Badge](https://img.shields.io/badge/Clean%20Architecture-purple)
![Static Badge](https://img.shields.io/badge/Coroutines%20Flow-purple)   
![Static Badge](https://img.shields.io/badge/Java-17-red)
![Static Badge](https://img.shields.io/badge/Kotlin-2.0.0-red)
![Static Badge](https://img.shields.io/badge/Kotlin%20Coroutines-1.8.0-red)
![Static Badge](https://img.shields.io/badge/Kotlin%20Serialization-1.6.3-red)
![Static Badge](https://img.shields.io/badge/Ktlint-1.3.0-red)   
![Static Badge](https://img.shields.io/badge/Dagger%20Hilt-2.50-red)
![Static Badge](https://img.shields.io/badge/Paging-3.3.0-red)
![Static Badge](https://img.shields.io/badge/Room-2.6.1-red)
![Static Badge](https://img.shields.io/badge/OkHttp-4.12.0-red)
![Static Badge](https://img.shields.io/badge/Retrofit-2.9.0-red)
![Static Badge](https://img.shields.io/badge/Glide-4.15.1-red)

- https://newsapi.org
- Search worldwide news with code.
  Locate articles and breaking news headlines from news sources and blogs across the web with our JSON API.

&nbsp;

## Authentication

- You need to register `news.api.key` in your `local.properties` file.
- news.api.key = "`API key issued by the site`"

&nbsp;

## Top headlines API

- ![Static Badge](https://img.shields.io/badge/GET-blue) [https://newsapi.org/v2/top-headlines?country=kr&apiKey={authorization}&page={page}&pageSize={size}](https://newsapi.org/docs/endpoints/top-headlines)

&nbsp;

## Clean Architecture Modules

![Static Badge](https://img.shields.io/badge/application-grey)   
![Static Badge](https://img.shields.io/badge/view-home-red)
![Static Badge](https://img.shields.io/badge/view-news-red)
![Static Badge](https://img.shields.io/badge/view-core-purple)   
![Static Badge](https://img.shields.io/badge/presenter-home-red)
![Static Badge](https://img.shields.io/badge/presenter-model.news-darkred)
![Static Badge](https://img.shields.io/badge/presenter-core-purple)   
![Static Badge](https://img.shields.io/badge/domain-news-red)
![Static Badge](https://img.shields.io/badge/domain-model.news-darkred)
![Static Badge](https://img.shields.io/badge/domain-model.core-purple)   
![Static Badge](https://img.shields.io/badge/data-news-red)
![Static Badge](https://img.shields.io/badge/data-core-purple)  
![Static Badge](https://img.shields.io/badge/data.remote-news-red)
![Static Badge](https://img.shields.io/badge/data.remote-core-purple)  
![Static Badge](https://img.shields.io/badge/data.cache-news-red)
![Static Badge](https://img.shields.io/badge/data.cache-database.news-darkred)
![Static Badge](https://img.shields.io/badge/data.cache-database-purple)

- [Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Dependencies

![Clean Architecture](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

- Set up dependencies in ![Static Badge](https://img.shields.io/badge/application-grey) module.


#### View &rarr; Presenter &rarr; Domain

![Static Badge](https://img.shields.io/badge/view-news-red)   
![Static Badge](https://img.shields.io/badge/view-home-red) &rarr;
![Static Badge](https://img.shields.io/badge/presenter-home-red) &rarr;
![Static Badge](https://img.shields.io/badge/domain-news-red)

#### Data source &rarr; Repository &rarr; Domain

![Static Badge](https://img.shields.io/badge/data.remote-news-red)   
![Static Badge](https://img.shields.io/badge/data.cache-news-red) &rarr;
![Static Badge](https://img.shields.io/badge/data-news-red) &rarr;
![Static Badge](https://img.shields.io/badge/domain-news-red)

### About Data Layer

![Data Layer](https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-data-overview.png)
- https://developer.android.com/topic/architecture/data-layer

&nbsp;

## Glide and Image cache

- Use [signature](https://bumptech.github.io/glide/doc/caching.html#cache-keys) for cache key.
- Use [DiskCache](https://bumptech.github.io/glide/doc/configuration.html#disk-cache) max size:
  [InternalCacheDiskCacheFactory(context, Long.MAX_VALUE)](library/src/main/java/com/bumptech/glide/load/engine/cache/InternalCacheDiskCacheFactory.java)
- Skip memory cache.
- In most cases, once an image has been loaded, it is fetched from the cache without being downloaded again.
- This will force the image URL to change from HTTP to HTTPS,
  but depending on the image server, an SSLHandshakeException may be thrown.

&nbsp;

## Paging with Room and Remote API

1. Check entities in Room.
2. If empty, call remote API.
3. Add response data in Room.
4. Get added entities in Room.

&nbsp;

## Support for different screen sizes

- On the phone screen, it displays in one column, then three columns after 600 DP.

&nbsp;

## Check viewed news.

- Load news in a full-screen in-app WebView.
- Many news webpages are not optimized for mobile, so we display loading status indicators and errors via a snack bar.
- Record the news you saw.
- For better usability, refresh the screen before recording, and rollback if recording fails.

&nbsp;

## Support Swipe Refresh.

- Clear all cached news and viewed history.
- And refresh news contents.

&nbsp;

## Device back buttons.

- Activity.onBackPressed is [deprecated](https://developer.android.com/reference/kotlin/androidx/activity/ComponentActivity#onBackPressed()) and uses OnBackPressedDispatcher.

&nbsp;

## TODO: MVI Architecture for UI state holders

- As more requirements are placed on a screen, it becomes difficult to manage the state of the screen.
- If state management becomes difficult at the Presenter Layer of a Clean Architecture, using an MVI Architecture at that layer makes it easier to manage state.

### [UI Layer](https://developer.android.com/topic/architecture/ui-layer)

![UI Layer](https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-ui-overview.png)

###  [Redux Fundamentals, Part 2: Concepts and Data Flow](https://redux.js.org/tutorials/fundamentals/part-2-concepts-data-flow/)

![Redux](https://redux.js.org/assets/images/ReduxDataFlowDiagram-49fa8c3968371d9ef6f2a1486bd40a26.gif)

### [Orbit MVI](https://github.com/orbit-mvi/orbit-mvi)

- Orbit is a Redux/MVI-like library - but without the baggage. It's so simple we think of it as MVVM+.

### [AirBnB Mavericks](https://github.com/airbnb/mavericks)

- Mavericks is the Android framework from Airbnb that we use for nearly all product development at Airbnb.

### [Spotify Mobius](https://github.com/spotify/mobius)

- Mobius is a functional reactive framework for managing state evolution and side-effects, with add-ons for connecting to Android UIs and RxJava Observables. It emphasizes separation of concerns, testability, and isolating stateful parts of the code.

### [CashApp Molecule](https://github.com/cashapp/molecule)

- Build a StateFlow stream using Jetpack Compose
