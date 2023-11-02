plugins {
    id("lol.android.library")
    id("lol.android.hilt")
}

android {
    namespace = "com.plznoanr.lol.core.common"

}

dependencies {

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}