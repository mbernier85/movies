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
    defaultConfig {
        applicationId = "im.bernier.movies"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
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
        kotlinCompilerExtensionVersion = "1.5.6"
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }

    namespace = "im.bernier.movies"
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.recyclerview)
    implementation(libs.swiperefreshlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    implementation(libs.hilt.navigation.compose)

    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.adapter.rxjava3)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    implementation(libs.legacy.support.v4)
    implementation(libs.room.runtime)
    implementation(libs.junit.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.rxjava3)

    implementation(libs.timber)
    implementation(libs.androidx.annotation)
    implementation(libs.glide)
    implementation(libs.compose)
    implementation(libs.dagger.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.dagger.compiler)

    debugImplementation(libs.leakcanary.android)

    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.navigation.compose)
    implementation("androidx.compose.material3:material3")

    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.runtime:runtime-rxjava3")

    testImplementation(libs.junit)

    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
