@file:Suppress("UnstableApiUsage")

plugins {
    id(
        "com.android.application"
    )
    id(
        "org.jetbrains.kotlin.android"
    )
    id(
        "com.google.dagger.hilt.android"
    )
    id(
        "kotlin-parcelize"
    )
    id(
        "com.google.devtools.ksp"
    )
    id(
        "kotlin-kapt"
    )
}

android {
    namespace = "app.mat.movie"

    compileSdk = 34

    defaultConfig {
        applicationId = "app.mat.movie"

        minSdk = 26

        targetSdk = 34

        versionCode = 1

        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)

        sourceSets {
            debug {
                kotlin.srcDir("build/generated/ksp/debug/kotlin")
            }
            release {
                kotlin.srcDir("build/generated/ksp/release/kotlin")
            }
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    //region Core
    val coreVersion = "1.12.0-alpha05"

    implementation(
        "androidx.core:core-ktx:$coreVersion"
    )
    //endregion Core

    //region Test
    val testVersion = "1.5.2"
    val testCoreVersion = "1.5.0"

    implementation(
        "androidx.test:runner:$testVersion"
    )
    androidTestImplementation(
        "androidx.test:core-ktx:$testCoreVersion"
    )
    //endregion Test

    //region JUnit
    val jUnitVersion = "1.1.5"

    androidTestImplementation(
        "androidx.test.ext:junit-ktx:$jUnitVersion"
    )
    //endregion JUnit

    // region Mockito
    val mockitoVersion = "5.3.1"

    testImplementation(
        "org.mockito:mockito-core:$mockitoVersion"
    )
    //endregion Mockito

    //region Hilt
    val hiltVersion = "2.46.1"

    implementation(
        "com.google.dagger:hilt-android:$hiltVersion"
    )
    kapt(
        "com.google.dagger:hilt-android-compiler:$hiltVersion"
    )
    //endregion Hilt

    //region Lifecycle
    val lifecycleVersion = "2.6.1"

    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    )
    implementation(
        "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    )
    implementation(
        "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"
    )
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
    )
    //endregion Lifecycle

    //region Retrofit
    val retrofitVersion = "2.9.0"

    implementation(
        "com.squareup.retrofit2:retrofit:$retrofitVersion"
    )
    implementation(
        "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    )
    //endregion Retrofit

    //region OkHttp
    val okHttpVersion = "5.0.0-alpha.2"

    implementation(
        "com.squareup.okhttp3:okhttp:$okHttpVersion"
    )
    implementation(
        "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    )
    //endregion OkHttp

    //region Moshi
    val moshiVersion = "1.14.0"

    ksp(
        "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
    )
    implementation(
        "com.squareup.moshi:moshi:$moshiVersion"
    )
    implementation(
        "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    )
    //endregion Moshi

    //region Gson
    val gsonVersion = "2.10.1"

    implementation(
        "com.google.code.gson:gson:$gsonVersion"
    )
    //endregion Moshi

    //region Timber
    val timberVersion = "5.0.1"

    implementation(
        "com.jakewharton.timber:timber:$timberVersion"
    )
    //endregion Timber

    //region Chucker
    val chuckerVersion = "3.5.2"

    debugImplementation(
        "com.github.chuckerteam.chucker:library:$chuckerVersion"
    )
    releaseImplementation(
        "com.github.chuckerteam.chucker:library-no-op:$chuckerVersion"
    )
    //endregion Chucker

    //region Room
    val roomVersion = "2.5.2"

    ksp(
        "androidx.room:room-compiler:$roomVersion"
    )
    implementation(
        "androidx.room:room-ktx:$roomVersion"
    )
    testImplementation(
        "androidx.room:room-testing:$roomVersion"
    )
    implementation(
        "androidx.room:room-paging:$roomVersion"
    )
    //endregion Room

    //region Paging
    val pagingVersion = "3.2.0-rc01"

    implementation(
        "androidx.paging:paging-runtime-ktx:$pagingVersion"
    )
    //endregion Paging

    //region Activity for Compose
    val activityVersion = "1.7.2"

    implementation(
        "androidx.activity:activity-compose:$activityVersion"
    )
    //endregion Activity for Compose

    //region Compose Navigation
    val composeNavigation = "2.6.0"

    implementation(
        "androidx.navigation:navigation-compose:$composeNavigation"
    )
    //endregion Compose Navigation

    //region Hilt Navigation Compose
    val hiltNavigationComposeVersion = "1.1.0-alpha01"

    implementation(
        "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"
    )
    //endregion Hilt Navigation Compose

    //region Paging for Compose
    val pagingComposeVersion = "3.2.0-rc01"

    implementation(
        "androidx.paging:paging-compose:$pagingComposeVersion"
    )
    //endregion Paging for Compose

    //region Compose
    val composeVersion = "1.6.0-alpha01"
    val materialVersion = "1.2.0-alpha03"
    val constraintLayoutVersion = "1.1.0-alpha10"

    implementation(
        "androidx.compose.ui:ui:$composeVersion"
    )
    implementation(
        "androidx.compose.foundation:foundation:$composeVersion"
    )
    implementation(
        "androidx.compose.animation:animation:$composeVersion"
    )
    implementation(
        "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    )
    debugImplementation(
        "androidx.compose.ui:ui-tooling:$composeVersion"
    )
    implementation(
        "androidx.compose.material3:material3-window-size-class:$materialVersion"
    )
    implementation(
        "androidx.compose.material:material-icons-extended:$composeVersion"
    )
    implementation(
        "androidx.compose.runtime:runtime-livedata:$composeVersion"
    )
    implementation(
        "androidx.compose.material3:material3:$materialVersion"
    )
    debugImplementation(
        "androidx.compose.ui:ui-test-manifest:$composeVersion"
    )
    androidTestImplementation(
        "androidx.compose.ui:ui-test-junit4:$composeVersion"
    )
    implementation(
        "androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion"
    )
    //endregion Compose

    //region Accompanist
    val accompanistVersion = "0.31.3-beta"

    implementation(
        "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
    )
    implementation(
        "com.google.accompanist:accompanist-placeholder:$accompanistVersion"
    )
    implementation(
        "com.google.accompanist:accompanist-flowlayout:$accompanistVersion"
    )
    implementation(
        "com.google.accompanist:accompanist-swiperefresh:$accompanistVersion"
    )
    implementation(
        "com.google.accompanist:accompanist-pager-indicators:$accompanistVersion"
    )
    implementation(
        "com.google.accompanist:accompanist-permissions:$accompanistVersion"
    )
    //endregion

    //region Palette
    val paletteVersion = "1.0.0"

    implementation(
        "androidx.palette:palette-ktx:$paletteVersion"
    )
    //endregion Palette

    //region Coil
    val coilVersion = "2.4.0"

    implementation(
        "io.coil-kt:coil-compose:$coilVersion"
    )
    //endregion Coil

    //region Lottie
    val lottieVersion = "6.0.0"

    implementation(
        "com.airbnb.android:lottie-compose:$lottieVersion"
    )
    //endregion Lottie

    //region CameraX
    val cameraXVersion = "1.3.0-beta01"

    implementation(
        "androidx.camera:camera-core:$cameraXVersion"
    )
    implementation(
        "androidx.camera:camera-camera2:$cameraXVersion"
    )
    implementation(
        "androidx.camera:camera-lifecycle:$cameraXVersion"
    )
    implementation(
        "androidx.camera:camera-video:$cameraXVersion"
    )
    implementation(
        "androidx.camera:camera-view:$cameraXVersion"
    )
    implementation(
        "androidx.camera:camera-mlkit-vision:$cameraXVersion"
    )
    implementation(
        "androidx.camera:camera-extensions:$cameraXVersion"
    )
    //endregion CameraX

    //region Text Recognition
    val textRecognitionVersion = "16.0.0"

    implementation(
        "com.google.mlkit:text-recognition:$textRecognitionVersion"
    )
    //endregion Text Recognition
}