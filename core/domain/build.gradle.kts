plugins {
    alias(libs.plugins.lol.android.library)
    alias(libs.plugins.lol.android.coroutines)
}

android {
    namespace = "com.plznoanr.lol.core.domain"
}


dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.data)

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.junit4)
}