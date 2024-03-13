plugins {
    alias(libs.plugins.lol.android.application)
    alias(libs.plugins.lol.android.hilt)
    alias(libs.plugins.lol.android.application.compose)
    alias(libs.plugins.lol.android.application.flavors)
    alias(libs.plugins.compose.compiler.report)
}

android {
    namespace = "com.plznoanr.lol"

    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.plznoanr.lol"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
        }
    }

}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.designsystem)
    implementation(projects.feature.home)
    implementation(projects.feature.search)
    implementation(projects.feature.bookmark)
    implementation(projects.feature.setting)
    implementation(projects.feature.summoner)
    implementation(projects.feature.spectator)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewModel.compose)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    implementation(libs.bundles.androidx.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)

    implementation(libs.timber)
//    implementation(libs.bundles.lottie)
//    implementation(libs.bundles.coil)
//    implementation(libs.coma)
//    implementation(libs.card.stack)

    testImplementation(libs.junit4)

}