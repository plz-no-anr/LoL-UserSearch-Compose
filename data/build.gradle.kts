plugins {
    androidLib
    kotlinAndroid
    kotlinKapt
    kotlinParcelize
    kotlinSerialization
    daggerHiltAndroid
}

android {
    namespace= AppConfig.APPLICATION_ID
    compileSdk = AppConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VERSION

        testInstrumentationRunner = AppConfig.ANDROID_TEST_INSTRUMENTATION
    }

    buildTypes {
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
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(domain)

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

    implementation(Dependencies.ThirdParty.KOTLIN_SERIALIZATION)
    implementationUnitTest()
}