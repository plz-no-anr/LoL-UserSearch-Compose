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
    implementationCoroutines()
    // Retrofit
    implementationRetrofit()
    // Room
    implementationRoom()
    // Hilt
    implementationHilt()
    // Timber
    implementation(Dependencies.ThirdParty.TIMBER)
    // Serialization
    implementation(Dependencies.ThirdParty.KOTLIN_SERIALIZATION)

    implementationUnitTest()
}