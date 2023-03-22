plugins {
    id(Dependencies.Plugins.APPLICATION)
    id(Dependencies.Plugins.KOTLIN_ANDROID)
    id(Dependencies.Plugins.KOTLIN_PARCELIZE)
    kotlin(Dependencies.Plugins.KOTLIN_KAPT)
    id(Dependencies.Plugins.DAGGER_HILT)
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
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    implementation(project(Dependencies.MultiModule.DATA))
    implementation(project(Dependencies.MultiModule.DOMAIN))

    implementation(Dependencies.AndroidX.CORE_KTX)
    implementation(Dependencies.AndroidX.APP_COMPAT)

    // Splash
    implementation(Dependencies.AndroidX.SPLASH_SCREEN)

    // UI
    implementation(Dependencies.ThirdParty.ANDROID_MATERIAL)
    implementation(Dependencies.AndroidX.SWIFE_REFRESH_LAYOUT)
    implementation(Dependencies.AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(Dependencies.AndroidX.NAVIGATION_UI_KTX)

    // Compose
    val composeBom = platform(Dependencies.AndroidX.Version.COMPOSE_BOM)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(Dependencies.AndroidX.Compose.MATERIAL3)
    implementation(Dependencies.AndroidX.Compose.MATERIAL3_WINDOW_SIZE)
    implementation(Dependencies.AndroidX.Compose.MATERIAL)
    implementation(Dependencies.AndroidX.Compose.MATERIAL_ICON_CORE)
    implementation(Dependencies.AndroidX.Compose.MATERIAL_ICON_EXTENDED)
    implementation(Dependencies.AndroidX.Compose.FOUNDATION)
    implementation(Dependencies.AndroidX.Compose.UI)
    implementation(Dependencies.AndroidX.Compose.UI_PREVIEW)
    implementation(Dependencies.AndroidX.Compose.ACTIVITY)
    implementation(Dependencies.AndroidX.Compose.VIEWMODEL)
    implementation(Dependencies.AndroidX.Compose.NAVIGATION)
    implementation(Dependencies.AndroidX.Compose.HILT)
    debugImplementation(Dependencies.AndroidTest.Compose.UI_TOOLING)
    androidTestImplementation(Dependencies.AndroidTest.Compose.UI_JUNIT4)
    debugImplementation(Dependencies.AndroidTest.Compose.UI_MANIFEST)

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

    // Test
    testImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.ROBOELETRIC)

    // Android Test
    androidTestImplementation(Dependencies.AndroidTest.JUNIT)
    androidTestImplementation(Dependencies.AndroidTest.ESPRESSO_CORE)

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

}