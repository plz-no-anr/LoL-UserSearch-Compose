plugins {
    alias(libs.plugins.lol.android.library)
    alias(libs.plugins.lol.android.library.compose)
    alias(libs.plugins.lol.kotlin.serialization)
}

android {
    namespace = "com.plznoanr.lol.core.navigation"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.navigation.compose)
}