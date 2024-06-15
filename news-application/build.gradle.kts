plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = libs.versions.namespace.get()
    compileSdk = libs.versions.sdk.target.get().toInt()

    defaultConfig {
        applicationId = libs.versions.namespace.get()
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = libs.versions.debug.minify.get().toBoolean()
            isDebuggable = libs.versions.debug.debuggable.get().toBoolean()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = libs.versions.release.minify.get().toBoolean()
            isDebuggable = libs.versions.release.debuggable.get().toBoolean()
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    // Clean architecture data.
    // TODO-DAGGER-HILT: Required when building with KSP.
    implementation(project(":library-data:remote:news"))
    implementation(project(":library-data:news"))

    // Clean architecture view.
    implementation(project(":news-view:core"))
    implementation(project(":news-view:home"))

    // Kotlin core.
    implementation(libs.kotlinx.coroutines)

    // AndroidX core.
    implementation(libs.androidx.core.ktx)

    // Dependency injection.
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}
