plugins {
    alias(libs.plugins.lol.jvm.library)
    alias(libs.plugins.lol.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.datetime)
}