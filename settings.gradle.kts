pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {

    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

// Root project.
rootProject.name = "News"
include(":news-application")

// Clean architecture view.
include(":news-view:core")
include(":news-view:home")
include(":news-view:news")

// Clean architecture presenter.
include(":news-presenter:model:news")
include(":news-presenter:core")
include(":news-presenter:home")

// Clean architecture domain.
include(":library-domain:model:core")
include(":library-domain:model:news")
include(":library-domain:news")

// Clean architecture data.
include(":library-data:core")
include(":library-data:news")
include(":library-data:remote:core")
include(":library-data:remote:news")
include(":library-data:cache:database:news")
include(":library-data:cache:database")
include(":library-data:cache:news")
