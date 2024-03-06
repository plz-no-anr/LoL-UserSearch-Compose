plugins {
    alias(libs.plugins.lol.android.library)
    alias(libs.plugins.lol.android.hilt)
    alias(libs.plugins.lol.android.room)
}

android {
    namespace = "com.plznoanr.lol.core.database"

}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.google.gson)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}