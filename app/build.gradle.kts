plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.flicker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.flicker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
}

dependencies {
    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Lifecycle (LiveData y ViewModel)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Core KTX
    implementation(libs.androidx.core.ktx)

    // AppCompat
    implementation(libs.androidx.appcompat)

    // Material Design
    implementation(libs.material)

    // Activity KTX
    implementation(libs.androidx.activity)

    // ConstraintLayout
    implementation(libs.androidx.constraintlayout)

    // Retrofit (Gson y Moshi Converters)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2.converter.moshi)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // MPAndroidChart
    implementation(libs.mpandroidchart)

    // Pruebas unitarias
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core.ktx)
    testImplementation(libs.core.testing)

    // Pruebas instrumentadas
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
    androidTestImplementation("org.hamcrest:hamcrest-library:2.2")
}