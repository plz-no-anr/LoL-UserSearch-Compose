plugins {
    alias(libs.plugins.lol.android.library)
}

android {
    namespace = "com.plznoanr.lol.core.mvibase"

}

dependencies {

    implementation(libs.timber)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewModel.compose)
}