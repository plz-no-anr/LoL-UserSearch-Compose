plugins {
    id("lol.android.library")
    id("lol.android.hilt")
}

android {
    namespace = "com.plznoanr.lol.core.common"

}

dependencies {
    api(libs.timber)
}