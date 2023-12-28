plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.arturboschDetekt)
    alias(libs.plugins.sentry)
}

android {
    namespace = "me.amirkazemzade.materialmusicplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.amirkazemzade.materialmusicplayer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            val buildUsingEnv = System.getenv("BUILD_USING_ENV")
            if (buildUsingEnv == "true") {
                signingConfig = signingConfigs.create("release") {
                    storeFile = file(System.getenv("SIGNING_KEY_STORE_PATH"))
                    storePassword = System.getenv("SIGNING_STORE_PASSWORD")
                    keyAlias = System.getenv("SIGNING_KEY_ALIAS")
                    keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
                }
            } else {
                signingConfig = signingConfigs.getByName("debug")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

detekt {
    parallel = true
    dependencies {
        detektPlugins(libs.twitterDetektPlugin)
    }
    config.setFrom("src/main/java/me/amirkazemzade/materialmusicplayer/presentation/config/detekt/detekt.yml")
}

dependencies {

    // Core

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)

    // Compose

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    //noinspection GradleDependency
    implementation(libs.androidx.material3)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.material)

    implementation(libs.chrisbanes.haze.jetpack.compose)

    // Lifecycle

    // Lifecycle utilities for Compose
    implementation(libs.androidx.lifecycle.runtime.compose)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Lifecycles only (without ViewModel or LiveData)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Lifecycle utilities for Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Player

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.session)

    // API

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)

    // DI

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    // Navigation Graph
    implementation(libs.koin.androidx.compose.navigation)

    // Test

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Debug

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
