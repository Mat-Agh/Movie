plugins {
    alias(
        libs.plugins.applicationPlugin
    )
    alias(
        libs.plugins.kotlinPlugin
    )
    alias(
        libs.plugins.hiltPlugin
    )
    alias(
        libs.plugins.parcelizePlugin
    )
    alias(
        libs.plugins.kspPlugin
    )
    alias(
        libs.plugins.kaptPlugin
    )
}

android {
    namespace = libs.versions.packageName.get()

    compileSdk = libs.versions.maxSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.packageName.get()

        minSdk = libs.versions.minSdk.get().toInt()

        targetSdk = libs.versions.maxSdk.get().toInt()

        versionCode = libs.versions.versionCode.get().toInt()

        versionName = libs.versions.versionName.get()

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
        jvmToolchain(
            libs.versions.jvmVersion.get().toInt()
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerVersion.get()
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    //region Core
    implementation(
        libs.core
    )
    //endregion Core

    //region Test
    implementation(
        libs.test
    )
    androidTestImplementation(
        libs.testCore
    )
    //endregion Test

    //region JUnit
    androidTestImplementation(
        libs.jUnit
    )
    //endregion JUnit

    // region Mockito
    testImplementation(
        libs.mokito
    )
    //endregion Mockito

    //region Hilt
    implementation(
        libs.hilt
    )
    kapt(
        libs.hiltCompiler
    )
    //endregion Hilt

    //region Lifecycle
    implementation(
        libs.lifecycleViewmodel
    )
    implementation(
        libs.lifecycleLivedata
    )
    implementation(
        libs.lifecycleCompose
    )
    implementation(
        libs.lifecycleViewmodelCompose
    )
    //endregion Lifecycle

    //region Retrofit
    implementation(
        libs.retrofit
    )
    implementation(
        libs.retrofitMoshiConverter
    )
    //endregion Retrofit

    //region OkHttp
    implementation(
        libs.okHttp
    )
    implementation(
        libs.okHttpInterceptor
    )
    //endregion OkHttp

    //region Moshi
    ksp(
        libs.moshiKotlinCodegen
    )
    implementation(
        libs.moshi
    )
    implementation(
        libs.moshiKotlin
    )
    //endregion Moshi

    //region Gson
    implementation(
        libs.gson
    )
    //endregion Moshi

    //region Timber
    implementation(
        libs.timberVersion
    )
    //endregion Timber

    //region Chucker
    debugImplementation(
        libs.chucker
    )
    releaseImplementation(
        libs.chuckerNoOp
    )
    //endregion Chucker

    //region Room
    ksp(
        libs.roomCompiler
    )
    implementation(
        libs.room
    )
    testImplementation(
        libs.roomTesting
    )
    implementation(
        libs.roomPaging
    )
    //endregion Room

    //region Paging
    implementation(
        libs.paging
    )
    //endregion Paging

    //region Activity for Compose
    implementation(
        libs.activityCompose
    )
    //endregion Activity for Compose

    //region Compose Navigation
    implementation(
        libs.composeNavigation
    )
    //endregion Compose Navigation

    //region Hilt Navigation Compose
    implementation(
        libs.hiltNavigationCompose
    )
    //endregion Hilt Navigation Compose

    //region Paging for Compose
    implementation(
        libs.paginCompose
    )
    //endregion Paging for Compose

    //region Compose
    implementation(
        libs.composeUI
    )
    implementation(
        libs.composeFoundation
    )
    implementation(
        libs.composeAnimation
    )
    implementation(
        libs.composeUIToolingPreview
    )
    debugImplementation(
        libs.composeUITooling
    )
    implementation(
        libs.composeMaterial3WindowSize
    )
    implementation(
        libs.composeMaterialIconsExtended
    )
    implementation(
        libs.composeLiveData
    )
    implementation(
        libs.composeMaterial3
    )
    debugImplementation(
        libs.composeUITestManifest
    )
    androidTestImplementation(
        libs.composeUITestJUnit
    )
    implementation(
        libs.composeConstraintLayout
    )
    //endregion Compose

    //region Accompanist
    implementation(
        libs.accompanistSystemUIController
    )
    implementation(
        libs.accompanistPlaceHolder
    )
    implementation(
        libs.accompanistFlowLayout
    )
    implementation(
        libs.accompanistSwipeRefresh
    )
    implementation(
        libs.accompanistPagerIndicators
    )
    implementation(
        libs.accompanistPermissions
    )
    //endregion

    //region Palette
    implementation(
        libs.palette
    )
    //endregion Palette

    //region Coil
    implementation(
        libs.coil
    )
    //endregion Coil

    //region Lottie
    implementation(
        libs.lottie
    )
    //endregion Lottie

    //region CameraX
    implementation(
        libs.cameraCore
    )
    implementation(
        libs.camera2
    )
    implementation(
        libs.cameraLifecycle
    )
    implementation(
        libs.cameraVideo
    )
    implementation(
        libs.cameraView
    )
    implementation(
        libs.cameraMLKitVision
    )
    implementation(
        libs.cameraExtensions
    )
    //endregion CameraX

    //region Text Recognition
    implementation(
        libs.textRecognition
    )
    //endregion Text Recognition
}