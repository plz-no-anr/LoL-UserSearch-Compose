
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
    implementationLifeCycle()
    // Coroutines
    implementationCoroutines()
    // Retrofit
    implementationRetrofit()
    // Room
    implementationRoom()
    // Hilt
    implementationHilt()
    // Timber
    implementation(Dependencies.ThirdParty.TIMBER)
    // Theme Adapter
    implementationThemeAdapter()
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