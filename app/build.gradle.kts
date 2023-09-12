plugins {
    androidApplication
    kotlinAndroid
    kotlinKapt
    kotlinParcelize
    daggerHiltAndroid
}

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.compileSdkVersion

    buildFeatures {
        compose = true
    }

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardFileName),
                AppConfig.proguardRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.javaCompatibility
        targetCompatibility = AppConfig.javaCompatibility
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtention
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(data)
    implementation(domain)
    implementation(Dependencies.AndroidX.core)
    implementation(Dependencies.AndroidX.appcompat)
    // Splash
    implementation(Dependencies.AndroidX.splashscreen)
    // Compose
    implementationCompose()
    // Lifecycle
    implementationLifecycle()
    // Coroutines
//    implementationCoroutines()
    // Retrofit
//    implementationRetrofit()
    // Room
//    implementationRoom()
    // Hilt
    implementationHilt()
    // Timber
    implementation(Dependencies.ThirdParty.timber)
    // Theme Adapter
    implementationThemeAdapter()
    // Image load
    implementation(Dependencies.ThirdParty.coil)
    // Lottie
    implementation(Dependencies.ThirdParty.lottieCompose)
    // Coma
    implementation(Dependencies.ThirdParty.coma)
    // ViewPager
    implementation(Dependencies.ThirdParty.viewPager)
    implementation(Dependencies.ThirdParty.viewPagerIndicators)
    // UnitTest
    implementationUnitTest()
    // Android Test
    implementationAndroidTest()
}