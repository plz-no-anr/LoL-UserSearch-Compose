plugins {
    alias(libs.plugins.lol.android.library)
    alias(libs.plugins.lol.android.hilt)
}

android {
    namespace = "com.plznoanr.lol.core.common"

}

dependencies {
    api(libs.timber)
}