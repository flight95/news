plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "${libs.versions.namespace.get()}.presenter.home"
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
        }
        release {
            isMinifyEnabled = libs.versions.release.minify.get().toBoolean()
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

    // Clean architecture domain.
    implementation(project(":library-domain:model:core"))
    implementation(project(":library-domain:model:news"))
    implementation(project(":library-domain:news"))

    // Clean architecture presenter.
    implementation(project(":news-presenter:model:news"))
    implementation(project(":news-presenter:core"))

    // Kotlin core.
    implementation(libs.kotlinx.coroutines)

    // AndroidX core.
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.savedstate)

    // AndroidX UI.
    implementation(libs.androidx.paging)

    // Dependency injection.
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}
