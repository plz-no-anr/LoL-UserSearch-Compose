plugins {
    id("lol.android.feature")
    id("lol.android.library.compose")
}

android {
    namespace = "com.plznoanr.lol.feature.search"

}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(libs.kotlinx.datetime)

}