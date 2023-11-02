plugins {
    id("lol.android.library")
    id("lol.android.hilt")
}

android {
    namespace = "com.plznoanr.lol.core.datastore"

}

dependencies {
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}