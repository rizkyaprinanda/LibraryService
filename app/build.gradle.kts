plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {


//    signingConfigs {
//        getByName("debug") {
//            storeFile = file("C:\\MyKeyStore\\keystore.jks")
//            storePassword = "Vivo2804"
//            keyAlias = "keybacsideapp"
//            keyPassword = "Vivo2804"
//        }
//    }


    namespace = "com.example.backside"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.backside"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
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

    implementation("com.caverock:androidsvg:1.4")
//    Fragment
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.5")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    testImplementation("junit:junit:4.13.2")
//    Dependencies Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    Dependencies OKHTTP
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.2")
//    Dependencies Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
//    Dependencies Picasso
    implementation ("com.squareup.picasso:picasso:2.8")
//    Dependencies Firebase
    implementation("com.google.firebase:firebase-auth:22.2.0")
    implementation("com.google.firebase:firebase-bom:32.5.0")
    implementation("com.google.firebase:firebase-analytics-ktx:21.5.0")
//    Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

//    Palette
    implementation ("androidx.palette:palette:1.0.0")

    implementation("androidx.cardview:cardview:1.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}