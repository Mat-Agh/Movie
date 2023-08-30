buildscript {
    repositories {
        mavenCentral()
        google()
    }
}

plugins {
    alias(
        libs.plugins.applicationPlugin
    ) apply false

    alias(
        libs.plugins.libraryPlugin
    ) apply false

    alias(
        libs.plugins.kotlinPlugin
    ) apply false

    alias(
        libs.plugins.parcelizePlugin
    ) apply false

    alias(
        libs.plugins.hiltPlugin
    ) apply false

    alias(
        libs.plugins.kspPlugin
    ) apply false

    alias(
        libs.plugins.kaptPlugin
    ) apply false
}

tasks.register(
    "clean"
) {
    delete(
        layout.buildDirectory
    )
}