plugins {
    alias(libs.plugins.lol.android.library)
    alias(libs.plugins.lol.android.library.compose)
}

android {
    namespace = "com.plznoanr.lol.core.designsystem"

}

dependencies {
    implementation(projects.core.model)
    api(projects.core.common)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)

    debugApi(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt.compose)
    implementation(libs.lottie.compose)
    implementation(libs.card.stack)
}