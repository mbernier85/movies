plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("androidx.room")
}

android {
    compileSdk = 34
    namespace = "im.bernier.movies"
    defaultConfig {
        applicationId = "im.bernier.movies"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            storeFile = file("release.keystore")
            storePassword = "onion-scrubber-fling-pension9"
            keyAlias = "movies"
            keyPassword = "onion-scrubber-fling-pension9"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.androidx.security.crypto)

    implementation(libs.hilt.navigation.compose)

    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava3)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    implementation(libs.room.runtime)
    implementation(libs.junit.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.rxjava3)

    implementation(libs.timber)
    implementation(libs.glide)
    implementation(libs.glide.compose)
    implementation(libs.dagger.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.dagger.compiler)

    debugImplementation(libs.leakcanary.android)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.runtime.rxjava3)
    implementation(libs.androidx.compose.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.manifest)
    androidTestImplementation(libs.androidx.compose.junit4)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    testImplementation(libs.junit)

    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
