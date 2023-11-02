plugins {
    id("lol.android.library")
    id("lol.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.plznoanr.lol.core.network"
    buildFeatures.buildConfig = true
    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("RIOT_BASE_URL")}\""
            )
        }
        release {
            isMinifyEnabled = true
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("RIOT_BASE_URL")}\""
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.timber)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}