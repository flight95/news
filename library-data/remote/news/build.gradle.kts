import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "${libs.versions.library.get()}.data.remote.news"
    compileSdk = libs.versions.sdk.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = libs.versions.debug.minify.get().toBoolean()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            Properties().let { properties ->
                properties.load(FileInputStream(project.rootProject.file("local.properties")))
                buildConfigField("String", "NEWS_API_KEY", properties["news.api.key"].toString())
            }
        }
        release {
            isMinifyEnabled = libs.versions.release.minify.get().toBoolean()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            Properties().let { properties ->
                properties.load(FileInputStream(project.rootProject.file("local.properties")))
                buildConfigField("String", "NEWS_API_KEY", properties["news.api.key"].toString())
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvm.get()
    }
    kotlin {
        jvmToolchain(libs.versions.jvm.get().toInt())
    }
}

dependencies {

    // Clean architecture data - remote.
    implementation(project(":library-data:remote:core"))

    // Clean architecture data.
    implementation(project(":library-data:news"))

    // Clean architecture domain.
    implementation(project(":library-domain:model:core"))
    implementation(project(":library-domain:model:news"))

    // Kotlin core.
    implementation(libs.kotlinx.coroutines)

    // AndroidX core.
    implementation(libs.androidx.core.ktx)

    // Dependency injection.
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    // Network.
    implementation(platform(libs.network.okhttp.bom))
    implementation(libs.network.okhttp)
    implementation(libs.network.okhttp.logging)
    implementation(libs.network.retrofit)
    implementation(libs.network.serialization)
    implementation(libs.network.serialization.converter)
}
