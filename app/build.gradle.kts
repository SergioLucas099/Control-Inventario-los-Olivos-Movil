plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.controlinventariolosolivosmovil"
    compileSdk = 35 // Actualización para cumplir con los requisitos de androidx.core:core-ktx:1.15.0

    defaultConfig {
        applicationId = "com.example.controlinventariolosolivosmovil"
        minSdk = 24
        targetSdk = 35 // Actualización para estar en línea con el compileSdk
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
    buildFeatures{
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Dependencias de Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics-ktx") // Analytics
    implementation("com.google.firebase:firebase-auth-ktx") // Autenticación (si es necesario)
    implementation("com.google.firebase:firebase-database-ktx") // Realtime Database
    implementation("com.google.firebase:firebase-firestore-ktx") // Firestore (si lo usas)
    implementation("com.google.firebase:firebase-storage-ktx:21.0.1") // Storage

    // Resto de dependencias
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.github.dhaval2404:imagepicker:2.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("com.github.qamarelsafadi:CurvedBottomNavigation:0.1.3")
}