plugins {
    alias(libs.plugins.lol.android.feature)
    alias(libs.plugins.lol.android.library.compose)
}

android {
    namespace = "com.plznoanr.lol.feature.search"

}

dependencies {
    implementation(libs.kotlinx.datetime)

}