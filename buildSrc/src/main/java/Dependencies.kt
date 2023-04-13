import org.gradle.api.artifacts.dsl.DependencyHandler
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

    object ClassPath {
        object Version {
            const val GRADLE_VERSION = "7.4.2"
            const val KOTLIN_VERSION = "1.8.10"
            const val HILT_VERSION = "2.45"
        }

        const val GRADLE = "com.android.tools.build:gradle:${Version.GRADLE_VERSION}"
        const val KOTLIN_GRADLE = "gradle-plugin"
        const val SERIALIZATION = "serialization"
        const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Version.HILT_VERSION}"
    }

    object AndroidX {

        object Version {
            const val CORE_KTX = "1.7.0"
            const val APP_COMPAT = "1.4.1"
            const val LIFECYCLE = "2.5.0-rc01"
            const val LIFECYCLE_EXTENSION = "2.2.0"
            const val NAVIGATION = "2.5.3"
            const val ROOM = "2.5.0"
            const val COMPOSE_BOM = "androidx.compose:compose-bom:2023.04.00"
        }

        const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Version.APP_COMPAT}"

        // Lifecycle
        const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.LIFECYCLE}"
        const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.LIFECYCLE}"
        const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.LIFECYCLE}"
        const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${Version.LIFECYCLE_EXTENSION}"

        // Room
        const val ROOM = "androidx.room:room-ktx:${Version.ROOM}"
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Version.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Version.ROOM}"

        object Compose {
            // Compose
            // Choose one of the following:
            // Material Design 3
            const val MATERIAL3 = "androidx.compose.material3:material3"
            // Optional - Add window size utils
            const val MATERIAL3_WINDOW_SIZE = "androidx.compose.material3:material3-window-size-class"
            // or Material Design 2
            const val MATERIAL = "androidx.compose.material:material"
            // Optional - Included automatically by material, only add when you need
            // the icons but not the material library (e.g. when using Material3 or a
            // custom design system based on Foundation)
            const val MATERIAL_ICON_CORE = "androidx.compose.material:material-icons-core"
            // Optional - Add full set of material icons
            const val MATERIAL_ICON_EXTENDED = "androidx.compose.material:material-icons-extended"
            // or skip Material Design and build directly on top of foundational components
            const val FOUNDATION = "androidx.compose.foundation:foundation"
            // or only import the main APIs for the underlying toolkit systems,
            // such as input and measurement/layout
            const val UI = "androidx.compose.ui:ui"
            // Android Studio Preview support
            const val UI_PREVIEW = "androidx.compose.ui:ui-tooling-preview"

            const val ACTIVITY = "androidx.activity:activity-compose:1.7.0"
            const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1"
            const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-compose:2.6.1"
            const val NAVIGATION = "androidx.navigation:navigation-compose:2.5.3"
            const val HILT = "androidx.hilt:hilt-navigation-compose:1.0.0"
        }

        // Splash
        const val SPLASH_SCREEN = "androidx.core:core-splashscreen:1.0.0"
    }

    object ThirdParty {

        object Version {
            const val ANDROID_MATERIAL = "1.6.0"
            const val COROUTINES = "1.6.0"
            const val RETROFIT = "2.9.0"
            const val HILT = "2.45"
            const val GLIDE = "4.14.2"
            const val VIEW_PAGER = "0.20.1"
            const val ACCOMPANIST = "0.28.0"
        }

        // Coroutines
        const val KOTLINX_COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINES}"
        const val KOTLINX_COROUTENS_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.COROUTINES}"

        // Retrofit
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
        const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Version.RETROFIT}"

        const val ANDROID_MATERIAL = "com.google.android.material:material:${Version.ANDROID_MATERIAL}"

        // Hilt
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Version.HILT}"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Version.HILT}"

        // Timber
        const val TIMBER = "com.jakewharton.timber:timber:5.0.1"

        // Lottie
        const val LOTTIE_COMPOSE = "com.airbnb.android:lottie-compose:4.2.0"

        //COIL
        const val COIL = "io.coil-kt:coil-compose:2.2.2"

        // Theme Adapter
        const val THEME_ADAPTER_APPCOMPAT = "com.google.accompanist:accompanist-themeadapter-appcompat:${Version.ACCOMPANIST}"
        const val THEME_ADAPTER_MATERIAL = "com.google.accompanist:accompanist-themeadapter-material:${Version.ACCOMPANIST}"
        const val THEME_ADAPTER_MATERIAL3 = "com.google.accompanist:accompanist-themeadapter-material3:${Version.ACCOMPANIST}"

        // ViewPager
        const val VIEW_PAGER = "com.google.accompanist:accompanist-pager:${Version.VIEW_PAGER}"
        const val VIEW_PAGER_INDICATORS = "com.google.accompanist:accompanist-pager-indicators:${Version.VIEW_PAGER}"

        // kotlin serialization
        const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"

    }

    object UnitTest {

        object Version {
            const val JUNIT = "4.13.2"
            const val ROBOELETRIC = "4.6.1"
            const val MOCKK = "1.12.4"
            const val OKHTTP3_MOCK_WEBSERVER = "4.9.2"
        }

        const val JUNIT = "junit:junit:${Version.JUNIT}"
        const val ROBOELETRIC = "org.robolectric:robolectric:${Version.ROBOELETRIC}"
        const val MOCKK = "io.mockk:mockk:${Version.MOCKK}"
        const val KOTLIN_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${ThirdParty.Version.COROUTINES}"
        const val OKHTTP3_MOCK_WEBSERVER = "com.squareup.okhttp3:mockwebserver:${Version.OKHTTP3_MOCK_WEBSERVER}"
    }

    object AndroidTest {

        object Version {
            const val JUNIT = "1.1.3"
            const val ESPRESSO_CORE = "3.4.0"
        }
        // UI Tests
        const val JUNIT = "androidx.test.ext:junit:${Version.JUNIT}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Version.ESPRESSO_CORE}"
        object Compose {
            const val UI_TOOLING = "androidx.compose.ui:ui-tooling"
            const val UI_JUNIT4 = "androidx.compose.ui:ui-test-junit4"
            const val UI_MANIFEST = "androidx.compose.ui:ui-test-manifest"
        }

    }
}

fun DependencyHandler.implementationHilt() {
    add("implementation", Dependencies.ThirdParty.HILT_ANDROID)
    add("kapt", Dependencies.ThirdParty.HILT_ANDROID_COMPILER)
}

fun DependencyHandler.implementationCompose(
) {
    arrayOf(
        platform(Dependencies.AndroidX.Version.COMPOSE_BOM),
        Dependencies.AndroidX.Compose.MATERIAL3,
        Dependencies.AndroidX.Compose.MATERIAL3_WINDOW_SIZE,
        Dependencies.AndroidX.Compose.MATERIAL,
        Dependencies.AndroidX.Compose.MATERIAL_ICON_CORE,
        Dependencies.AndroidX.Compose.MATERIAL_ICON_EXTENDED,
        Dependencies.AndroidX.Compose.FOUNDATION,
        Dependencies.AndroidX.Compose.UI,
        Dependencies.AndroidX.Compose.UI_PREVIEW,
        Dependencies.AndroidX.Compose.ACTIVITY,
        Dependencies.AndroidX.Compose.VIEWMODEL,
        Dependencies.AndroidX.Compose.RUNTIME,
        Dependencies.AndroidX.Compose.NAVIGATION,
        Dependencies.AndroidX.Compose.HILT,
    ).forEach { add("implementation", it) }

    add("debugImplementation", Dependencies.AndroidTest.Compose.UI_TOOLING)
    add("debugImplementation", Dependencies.AndroidTest.Compose.UI_MANIFEST)
    add("androidTestImplementation", Dependencies.AndroidTest.Compose.UI_JUNIT4)
}

fun DependencyHandler.implementationLifeCycle() {
    arrayOf(
        Dependencies.AndroidX.LIFECYCLE_EXTENSIONS,
        Dependencies.AndroidX.LIFECYCLE_LIVEDATA_KTX,
        Dependencies.AndroidX.LIFECYCLE_RUNTIME_KTX,
        Dependencies.AndroidX.LIFECYCLE_VIEWMODEL_KTX
    ).forEach { add("implementation", it) }
}


fun DependencyHandler.implementationCoroutines() {
    arrayOf(
        Dependencies.ThirdParty.KOTLINX_COROUTINES_CORE,
        Dependencies.ThirdParty.KOTLINX_COROUTENS_ANDROID
    ).forEach { add("implementation", it) }
}

fun DependencyHandler.implementationRetrofit() {
    arrayOf(
        Dependencies.ThirdParty.RETROFIT,
        Dependencies.ThirdParty.RETROFIT_CONVERTER_GSON
    ).forEach { add("implementation", it) }
}

fun DependencyHandler.implementationRoom() {
    arrayOf(
        Dependencies.AndroidX.ROOM,
        Dependencies.AndroidX.ROOM_RUNTIME
    ).forEach { add("implementation", it) }
    add("kapt", Dependencies.AndroidX.ROOM_COMPILER)
}

fun DependencyHandler.implementationThemeAdapter() {
    arrayOf(
        Dependencies.ThirdParty.THEME_ADAPTER_APPCOMPAT,
        Dependencies.ThirdParty.THEME_ADAPTER_MATERIAL,
        Dependencies.ThirdParty.THEME_ADAPTER_MATERIAL3,
    ).forEach { add("implementation", it) }
}



fun DependencyHandler.implementationUnitTest() {
    arrayOf(
        Dependencies.UnitTest.JUNIT,
        Dependencies.UnitTest.ROBOELETRIC,
        Dependencies.UnitTest.MOCKK,
        Dependencies.UnitTest.KOTLIN_COROUTINES_TEST,
        Dependencies.UnitTest.OKHTTP3_MOCK_WEBSERVER,
    ).forEach { add("testImplementation", it) }
}

fun DependencyHandler.implementationAndroidTest() {
    arrayOf(
        Dependencies.AndroidTest.JUNIT,
        Dependencies.AndroidTest.ESPRESSO_CORE,
    ).forEach { add("androidTestImplementation", it) }
}