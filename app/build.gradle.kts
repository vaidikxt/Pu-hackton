plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.yourheathhero"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.yourheathhero"
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
}

dependencies {
    implementation ("org.tensorflow:tensorflow-lite:2.10.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.4.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("org.tensorflow:tensorflow-lite:2.4.0")
    implementation ("org.tensorflow:tensorflow-lite-select-tf-ops:2.4.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.1.0")

    implementation ("com.makeramen:roundedimageview:2.3.0")
    implementation ("com.airbnb.android:lottie:6.4.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}