
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.game1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.game1"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //google maps
   // implementation ("com.google.android.gms:play-services-maps:19.0.0")

    //gson
    implementation (libs.gson)

    //implementation ("androidx.recyclerview:recyclerview:1.2.1")

    implementation ("com.google.android.gms:play-services-location:21.3.0")


}