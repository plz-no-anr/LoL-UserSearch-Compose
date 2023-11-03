plugins {
    id("lol.android.feature")
    id("lol.android.library.compose")
}

android {
    namespace = "com.plznoanr.lol.feature.setting"
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
}