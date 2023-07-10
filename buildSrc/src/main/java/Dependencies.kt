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
        const val gradle = "com.android.tools.build:gradle:${Version.Plugin.gradle}"
        const val kotlinGradle = "gradle-plugin"
        const val serialization = "serialization"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Version.Plugin.hilt}"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:${Version.AndroidX.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Version.AndroidX.appCompat}"
        // Splash
        const val splashscreen = "androidx.core:core-splashscreen:${Version.AndroidX.splash}"

        // Lifecycle
        const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.AndroidX.lifecycle}"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.AndroidX.lifecycle}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.AndroidX.lifecycle}"

        // Room
        const val room = "androidx.room:room-ktx:${Version.AndroidX.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Version.AndroidX.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Version.AndroidX.room}"

        // DataStore
        const val dataStore = "androidx.datastore:datastore-preferences:${Version.AndroidX.dataStore}"
        const val dataStoreCore = "androidx.datastore:datastore-preferences-core:${Version.AndroidX.dataStore}"

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

            const val activity = "androidx.activity:activity-compose:${Version.AndroidX.activity}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.AndroidX.lifecycle}"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-compose:${Version.AndroidX.lifecycle}"
            const val navigation = "androidx.navigation:navigation-compose:${Version.AndroidX.navigation}"
            const val hilt = "androidx.hilt:hilt-navigation-compose:${Version.AndroidX.navigationCompose}"
        }

    }

    object ThirdParty {
        // Coroutines
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.ThirdParty.coroutine}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.ThirdParty.coroutine}"

        // Retrofit
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.ThirdParty.retrofit}"
        const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Version.ThirdParty.retrofit}"
        const val retrofitConverterSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Version.ThirdParty.kotlinSerializationConverter}"

        const val androidMaterial = "com.google.android.material:material:${Version.ThirdParty.material}"

        // Hilt
        const val hilt = "com.google.dagger:hilt-android:${Version.Plugin.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.Plugin.hilt}"

        // Timber
        const val timber = "com.jakewharton.timber:timber:${Version.ThirdParty.timber}"

        // Lottie
        const val lottieCompose = "com.airbnb.android:lottie-compose:${Version.ThirdParty.Compose.lottie}"

        //COIL
        const val coil = "io.coil-kt:coil-compose:${Version.ThirdParty.Compose.coil}"

        // Theme Adapter
        const val themeAdapter = "com.google.accompanist:accompanist-themeadapter-appcompat:${Version.ThirdParty.accompanist}"
        const val themeAdapterMaterial = "com.google.accompanist:accompanist-themeadapter-material:${Version.ThirdParty.accompanist}"
        const val themeAdapterMaterial3 = "com.google.accompanist:accompanist-themeadapter-material3:${Version.ThirdParty.accompanist}"

        // ViewPager
        const val viewPager = "com.google.accompanist:accompanist-pager:${Version.ThirdParty.viewPager}"
        const val viewPagerIndicators = "com.google.accompanist:accompanist-pager-indicators:${Version.ThirdParty.viewPager}"

        // kotlin serialization
        const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.ThirdParty.kotlinSerialization}"
    }

    object UnitTest {
        const val junit = "junit:junit:${Version.UnitTest.junit}"
        const val robolectric = "org.robolectric:robolectric:${Version.UnitTest.roboelectric}"
        const val mockk = "io.mockk:mockk:${Version.UnitTest.mockk}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.ThirdParty.coroutine}"
        const val okhttp3Mock = "com.squareup.okhttp3:mockwebserver:${Version.UnitTest.okhttp3MockWebServer}"
    }

    object AndroidTest {
        // UI Tests
        const val junit = "androidx.test.ext:junit:${Version.AndroidTest.junit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Version.AndroidTest.espresso}"
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

fun DependencyHandler.implementationCompose(
) {
    arrayOf(
        platform(Version.AndroidX.compose),
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
    add("androidTestImplementation", platform(Version.AndroidX.compose))
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