plugins {
    androidLib
    kotlinAndroid
    kotlinKapt
    kotlinParcelize
    kotlinSerialization
    daggerHiltAndroid
}

android {
    namespace = NameSpace.data
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk = AppConfig.minSdkVersion

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(domain)
    // Coroutines
    api(Dependencies.ThirdParty.coroutinesAndroid)
    // Retrofit
    implementationRetrofit()
    // Room
    implementationRoom()
    // Hilt
    implementationHilt()
    // Timber
    implementation(Dependencies.ThirdParty.timber)
    // Serialization
    implementation(Dependencies.ThirdParty.kotlinSerialization)

    implmentationDataStore()

    implementationUnitTest()

    implementationAndroidTest()
}