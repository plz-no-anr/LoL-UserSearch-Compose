plugins {
    id("lol.android.library")
    id("lol.android.hilt")
    id("lol.android.room")
}

android {
    namespace = "com.plznoanr.lol.core.database"

}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.google.gson)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}