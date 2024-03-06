plugins {
    alias(libs.plugins.lol.android.feature)
    alias(libs.plugins.lol.android.library.compose)
}

android {
    namespace = "com.plznoanr.lol.feature.home"

}

dependencies {
    implementation(libs.androidx.compose.material)
}