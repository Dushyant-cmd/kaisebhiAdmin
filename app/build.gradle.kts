plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.kaisebhiadmin"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.kaisebhiadmin"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
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
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.media3:media3-exoplayer:1.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //ViewModel and LiveData
    val lifecycle_version = "2.2.0"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //Firebase libraries
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")

    //google play services
    implementation("com.google.gms:google-services:4.3.15")
    //circle image view
    implementation("de.hdodenhof:circleimageview:3.0.0")
    // ExoPlayer
//    implementation("com.google.android.exoplayer:exoplayer:r2.4.0")
//// for core support in exoplayer.
//    implementation("com.google.android.exoplayer:exoplayer-core:r2.4.0")
//// for adding dash support in our exoplayer.
//    implementation("com.google.android.exoplayer:exoplayer-dash:r2.4.0")
//// for adding hls support in exoplayer.
//    implementation("com.google.android.exoplayer:exoplayer-hls:r2.4.0")
//// for smooth streaming of video in our exoplayer.
//    implementation("com.google.android.exoplayer:exoplayer-smoothstreaming:r2.4.0")
//// for generating default ui of exoplayer
//    implementation("com.google.android.exoplayer:exoplayer-ui:r2.4.0")
    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
}