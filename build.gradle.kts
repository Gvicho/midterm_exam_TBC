// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false // for Hilt
    id("com.google.gms.google-services") version "4.4.0" apply false // for FireBase
}

// safeargs
buildscript {

    repositories {
        google()
        mavenCentral() // for glide
        maven(url = "https://jitpack.io")
    }

    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
    }
}