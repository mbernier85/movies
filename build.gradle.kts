// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.13.1" apply false
    id("com.android.library") version "8.13.1" apply false
    id("com.google.devtools.ksp") version "2.3.2" apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.dependency.analysis)
}

buildscript {
    dependencies {
        classpath(libs.kotlin.serialization)
        classpath(libs.google.services)
        classpath(libs.firebase.appdistribution.gradle)
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.androidx.room.gradle.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
