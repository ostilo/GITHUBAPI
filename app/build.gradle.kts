plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.github.githubsearch"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.github.githubsearch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        android.buildFeatures.buildConfig  = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField ("String",  "GITHUB_API_BASE_URL", "\"https://api.github.com\"")
        buildConfigField ("String",  "GITHUB_API_KEY", "\"https://api.github.com\"")



    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Navigation
    implementation(libs.navigation.compose)
    // Kotlin Viewmodel
    implementation(libs.lifecycle.viewmodel.ktx)
    // ViewModel utilities for Compose
    implementation(libs.lifecycle.viewmodel.compose)
    // Kotlin LiveData
    implementation(libs.lifecycle.livedata.ktx)
    // Lifecycles only (without ViewModel or LiveData)
    implementation(libs.lifecycle.runtime.ktx)
    // Lifecycle utilities for Compose
    implementation(libs.lifecycle.runtime.compose)
    // Koin for compose - Adds Jetpack Compose specific features:
    implementation(libs.koin.androidx.compose)
    // Builds on top of koin-core - Adds Android-specific features:
    implementation(libs.koin.androidx.android)
    // Base Koin dependency - Contains core DI functionality
    implementation(libs.koin.core)
    // Json serialization
    implementation(libs.kotlinx.serialization.json)

    // Gson
    implementation(libs.gson)

    implementation(libs.squareup.retrofit2.retrofit)

    implementation(libs.squareup.retrofit2.converter)

    implementation(libs.okhttp3.logging)

//    implementation(libs.io.mockk)



}