buildscript {
    repositories {
        mavenCentral()
        google()
    }
}

plugins {
    id(
        "com.android.application"
    ) version "8.0.2" apply false

    id(
        "com.android.library"
    ) version "8.0.2" apply false

    id(
        "org.jetbrains.kotlin.android"
    ) version "1.8.22" apply false

    id(
        "com.google.dagger.hilt.android"
    ) version "2.46.1" apply false

    id(
        "com.google.devtools.ksp"
    ) version "1.8.22-1.0.11" apply false

}

tasks.register(
    "clean"
) {
    delete(
        rootProject.buildDir
    )
}