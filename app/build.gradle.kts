plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization") version "2.0.0"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.bibliosphere"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bibliosphere"
        minSdk = 25
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2025.05.00"))
    //android material 3
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    implementation("androidx.compose.material3:material3:1.3.2")
    //ViewModel
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.7.8")
    //iconos
    implementation("androidx.compose.material:material-icons-extended")
    //navigation
    implementation("androidx.navigation:navigation-compose:2.8.2")
    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    //firebase
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    //google
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    //implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    //implementation("androidx.credentials:credentials:1.5.0")

    // Para Credential Manager
    implementation ("androidx.credentials:credentials:1.2.0")
    implementation ("androidx.credentials:credentials-play-services-auth:1.2.0")

    // Para Google Identity
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.0.1")
//    //hilt
//    implementation("com.google.dagger:hilt-android:2.56.2")
//    ksp("com.google.dagger:hilt-android-compiler:2.56.2")
    //gson
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.privacysandbox.tools:tools-core:1.0.0-alpha13")
    implementation("androidx.wear.compose:compose-material:1.4.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}