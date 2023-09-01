import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

inline val PluginDependenciesSpec.androidApplication: PluginDependencySpec
    get() = id("com.android.application")
inline val PluginDependenciesSpec.androidLib: PluginDependencySpec
    get() = id("com.android.library")
inline val PluginDependenciesSpec.kotlinAndroid: PluginDependencySpec
    get() = kotlin("android")
inline val PluginDependenciesSpec.kotlin: PluginDependencySpec
    get() = kotlin("jvm")
inline val PluginDependenciesSpec.kotlinKapt: PluginDependencySpec
    get() = kotlin("kapt")
inline val PluginDependenciesSpec.ksp: PluginDependencySpec
    get() = id("com.google.devtools.ksp")
inline val PluginDependenciesSpec.kotlinParcelize: PluginDependencySpec
    get() = id("kotlin-parcelize")
inline val PluginDependenciesSpec.daggerHiltAndroid: PluginDependencySpec
    get() = id("dagger.hilt.android.plugin")
inline val PluginDependenciesSpec.kotlinSerialization: PluginDependencySpec
    get() = kotlin("plugin.serialization")
inline val PluginDependenciesSpec.javaLib: PluginDependencySpec
    get() = id("java-library")

// Multi Module
inline val DependencyHandler.data get() = project(":data")
inline val DependencyHandler.domain get() = project(":domain")

object Dependencies {

    object Plugin {
        const val application = "com.android.application"
        const val kotlin = "android"
        const val serialization = "plugin.serialization"
        const val hilt = "com.google.dagger.hilt.android"
        const val ksp = "com.google.devtools.ksp"
        const val kapt = "kapt"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        // Splash
        const val splashscreen = "androidx.core:core-splashscreen:${Versions.AndroidX.splash}"

        // Lifecycle
        const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"

        // Room
        const val room = "androidx.room:room-ktx:${Versions.AndroidX.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.AndroidX.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.AndroidX.room}"

        // DataStore
        const val dataStore = "androidx.datastore:datastore-preferences:${Versions.AndroidX.dataStore}"
        const val dataStoreCore = "androidx.datastore:datastore-preferences-core:${Versions.AndroidX.dataStore}"

        object Compose {
            // Compose
            // Choose one of the following:
            // Material Design 3
            const val material3 = "androidx.compose.material3:material3"
            // Optional - Add window size utils
            const val material3Window = "androidx.compose.material3:material3-window-size-class"
            // or Material Design 2
            const val material = "androidx.compose.material:material"
            // Optional - Included automatically by material, only add when you need
            // the icons but not the material library (e.g. when using Material3 or a
            // custom design system based on Foundation)
            const val materialIcons = "androidx.compose.material:material-icons-core"
            // Optional - Add full set of material icons
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended"
            // or skip Material Design and build directly on top of foundational components
            const val foundation = "androidx.compose.foundation:foundation"
            // or only import the main APIs for the underlying toolkit systems,
            // such as input and measurement/layout
            const val ui = "androidx.compose.ui:ui"
            // Android Studio Preview support
            const val uiPreview = "androidx.compose.ui:ui-tooling-preview"

            const val activity = "androidx.activity:activity-compose:${Versions.AndroidX.activity}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.AndroidX.lifecycle}"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.AndroidX.lifecycle}"
            const val navigation = "androidx.navigation:navigation-compose:${Versions.AndroidX.navigation}"
            const val hilt = "androidx.hilt:hilt-navigation-compose:${Versions.AndroidX.navigationCompose}"
        }

    }

    object ThirdParty {
        // Coroutines
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.ThirdParty.coroutine}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.ThirdParty.coroutine}"

        // OkHttp
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.ThirdParty.okHttp}"
        const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.ThirdParty.okHttp}"


        // Retrofit
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.ThirdParty.retrofit}"
        const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.ThirdParty.retrofit}"
        const val retrofitConverterSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.ThirdParty.kotlinSerializationConverter}"

        const val androidMaterial = "com.google.android.material:material:${Versions.ThirdParty.material}"

        // Hilt
        const val hilt = "com.google.dagger:hilt-android:${Versions.Plugin.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Plugin.hilt}"

        // Timber
        const val timber = "com.jakewharton.timber:timber:${Versions.ThirdParty.timber}"

        // Lottie
        const val lottieCompose = "com.airbnb.android:lottie-compose:${Versions.ThirdParty.Compose.lottie}"

        //COIL
        const val coil = "io.coil-kt:coil-compose:${Versions.ThirdParty.Compose.coil}"

        // Theme Adapter
        const val themeAdapter = "com.google.accompanist:accompanist-themeadapter-appcompat:${Versions.ThirdParty.accompanist}"
        const val themeAdapterMaterial = "com.google.accompanist:accompanist-themeadapter-material:${Versions.ThirdParty.accompanist}"
        const val themeAdapterMaterial3 = "com.google.accompanist:accompanist-themeadapter-material3:${Versions.ThirdParty.accompanist}"

        // ViewPager
        const val viewPager = "com.google.accompanist:accompanist-pager:${Versions.ThirdParty.viewPager}"
        const val viewPagerIndicators = "com.google.accompanist:accompanist-pager-indicators:${Versions.ThirdParty.viewPager}"

        // kotlin serialization
        const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.ThirdParty.kotlinSerialization}"

        // Coma Mvi
        const val coma = "io.github.plz-no-anr:coma:1.0.2"
    }

    object UnitTest {
        const val junit = "junit:junit:${Versions.UnitTest.junit}"
        const val robolectric = "org.robolectric:robolectric:${Versions.UnitTest.roboelectric}"
        const val mockk = "io.mockk:mockk:${Versions.UnitTest.mockk}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.ThirdParty.coroutine}"
        const val okhttp3Mock = "com.squareup.okhttp3:mockwebserver:${Versions.UnitTest.okhttp3MockWebServer}"
    }

    object AndroidTest {
        // UI Tests
        const val junit = "androidx.test.ext:junit:${Versions.AndroidTest.junit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espresso}"

        object Compose {
            const val junit = "androidx.compose.ui:ui-test-junit4"
            const val uiTooling = "androidx.compose.ui:ui-tooling"
            const val uiManifest = "androidx.compose.ui:ui-test-manifest"
        }
    }
}

fun DependencyHandlerScope.implementationHilt() {
    add("implementation", Dependencies.ThirdParty.hilt)
    add("kapt", Dependencies.ThirdParty.hiltCompiler)
}

fun DependencyHandler.implementationCompose() {
    arrayOf(
        platform(Versions.AndroidX.compose),
        Dependencies.AndroidX.Compose.material3,
        Dependencies.AndroidX.Compose.material3Window,
        Dependencies.AndroidX.Compose.material,
        Dependencies.AndroidX.Compose.materialIcons,
        Dependencies.AndroidX.Compose.materialIconsExtended,
        Dependencies.AndroidX.Compose.foundation,
        Dependencies.AndroidX.Compose.ui,
        Dependencies.AndroidX.Compose.uiPreview,
        Dependencies.AndroidX.Compose.activity,
        Dependencies.AndroidX.Compose.viewModel,
        Dependencies.AndroidX.Compose.runtime,
        Dependencies.AndroidX.Compose.navigation,
        Dependencies.AndroidX.Compose.hilt,
    ).forEach { add("implementation", it) }

    add("debugImplementation", Dependencies.AndroidTest.Compose.uiTooling)
    add("debugImplementation", Dependencies.AndroidTest.Compose.uiManifest)
    add("androidTestImplementation", platform(Versions.AndroidX.compose))
    add("androidTestImplementation", Dependencies.AndroidTest.Compose.junit)
}

fun DependencyHandler.implementationLifecycle() {
    arrayOf(
        Dependencies.AndroidX.lifecycleLiveData,
        Dependencies.AndroidX.lifecycleRuntime,
        Dependencies.AndroidX.lifecycleViewModel
    ).forEach { add("implementation", it) }
}


fun DependencyHandler.implementationCoroutines() {
    arrayOf(
        Dependencies.ThirdParty.coroutinesCore,
        Dependencies.ThirdParty.coroutinesAndroid
    ).forEach { add("api", it) }
}

fun DependencyHandler.implementationOkHttp() {
    arrayOf(
        Dependencies.ThirdParty.okHttp,
        Dependencies.ThirdParty.okHttpInterceptor
    ).forEach { add("api", it) }
}

fun DependencyHandler.implementationRetrofit() {
    arrayOf(
        Dependencies.ThirdParty.retrofit,
        Dependencies.ThirdParty.retrofitConverterGson,
        Dependencies.ThirdParty.retrofitConverterSerialization
    ).forEach { add("api", it) }
}

fun DependencyHandler.implementationRoom() {
    arrayOf(
        Dependencies.AndroidX.room,
        Dependencies.AndroidX.roomRuntime
    ).forEach { add("implementation", it) }
    add("kapt", Dependencies.AndroidX.roomCompiler)
}

fun DependencyHandler.implementationThemeAdapter() {
    arrayOf(
        Dependencies.ThirdParty.themeAdapter,
        Dependencies.ThirdParty.themeAdapterMaterial,
        Dependencies.ThirdParty.themeAdapterMaterial3,
    ).forEach { add("implementation", it) }
}



fun DependencyHandler.implementationUnitTest() {
    arrayOf(
        Dependencies.UnitTest.junit,
        Dependencies.UnitTest.robolectric,
        Dependencies.UnitTest.mockk,
        Dependencies.UnitTest.coroutines,
        Dependencies.UnitTest.okhttp3Mock,
    ).forEach { add("testImplementation", it) }
}

fun DependencyHandler.implementationAndroidTest() {
    arrayOf(
        Dependencies.AndroidTest.junit,
        Dependencies.AndroidTest.espresso,
    ).forEach { add("androidTestImplementation", it) }
}

fun DependencyHandler.implmentationDataStore() {
    arrayOf(
        Dependencies.AndroidX.dataStore,
        Dependencies.AndroidX.dataStoreCore,
    ).forEach { add("implementation", it) }
}