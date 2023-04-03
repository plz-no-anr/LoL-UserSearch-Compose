
plugins {
    androidApplication
    kotlinAndroid
    kotlinKapt
    kotlinParcelize
    daggerHiltAndroid
}

android {
    namespace = AppConfig.APPLICATION_ID
    compileSdk = AppConfig.COMPILE_SDK_VERSION

    buildFeatures {
        compose = true
    }

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK_VERSION
        targetSdk = AppConfig.TARGET_SDK_VERSION
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = AppConfig.ANDROID_TEST_INSTRUMENTATION
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {

        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(AppConfig.PROGUARD_FILE_NAME),
                AppConfig.PROGUARD_RULES
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.JAVA_COMPATIBILITY
        targetCompatibility = AppConfig.JAVA_COMPATIBILITY
    }
    kotlinOptions {
        jvmTarget = AppConfig.JAVA_JVM_TARGET
    }
    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.KOTLIN_COMPILER_EXTENTION
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(data)
    implementation(domain)

    implementation(Dependencies.AndroidX.CORE_KTX)
    implementation(Dependencies.AndroidX.APP_COMPAT)

    // Splash
    implementation(Dependencies.AndroidX.SPLASH_SCREEN)

    // Compose
    implementationCompose()

    // Lifecycle
    implementation(Dependencies.AndroidX.LIFECYCLE_LIVEDATA_KTX)
    implementation(Dependencies.AndroidX.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependencies.AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.AndroidX.LIFECYCLE_EXTENSIONS)

    // Coroutines
    implementation(Dependencies.ThirdParty.KOTLINX_COROUTINES_CORE)
    implementation(Dependencies.ThirdParty.KOTLINX_COROUTENS_ANDROID)

    // Retrofit
    implementation(Dependencies.ThirdParty.RETROFIT)
    implementation(Dependencies.ThirdParty.RETROFIT_CONVERTER_GSON)

    // Room
    implementation(Dependencies.AndroidX.ROOM)
    implementation(Dependencies.AndroidX.ROOM_RUNTIME)
    kapt(Dependencies.AndroidX.ROOM_COMPILER)

    // Hilt
    implementation(Dependencies.ThirdParty.HILT_ANDROID)
    kapt(Dependencies.ThirdParty.HILT_ANDROID_COMPILER)

    // Timber
    implementation(Dependencies.ThirdParty.TIMBER)

    // Theme Adapter
    implementation(Dependencies.ThirdParty.THEME_ADAPTER_APPCOMPAT)
    implementation(Dependencies.ThirdParty.THEME_ADAPTER_MATERIAL)
    implementation(Dependencies.ThirdParty.THEME_ADAPTER_MATERIAL3)

    // Image load
    implementation(Dependencies.ThirdParty.COIL)

    // Lottie
    implementation(Dependencies.ThirdParty.LOTTIE_COMPOSE)

    // ViewPager
    implementation(Dependencies.ThirdParty.VIEW_PAGER)
    implementation(Dependencies.ThirdParty.VIEW_PAGER_INDICATORS)

    // UnitTest
    implementationUnitTest()

    // Android Test
    implementationAndroidTest()
}