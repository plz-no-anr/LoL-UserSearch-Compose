plugins {
    id("lol.android.library")
    id("lol.android.coroutines")
}

android {
    namespace = "com.plznoanr.lol.core.domain"
}


dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.junit4)
}