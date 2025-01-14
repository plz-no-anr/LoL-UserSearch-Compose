plugins {
    alias(libs.plugins.lol.android.library)
    alias(libs.plugins.lol.android.library.compose)
}

android {
    namespace = "com.plznoanr.lol.core.ui"
}

dependencies {
    implementation(projects.core.model)
    api(projects.core.designsystem)
    implementation(libs.card.stack)
    implementation(libs.kotlinx.collections.immutable)
}