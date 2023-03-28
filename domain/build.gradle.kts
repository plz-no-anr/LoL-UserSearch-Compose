plugins {
    id(Dependencies.Plugins.JAVA_LIBRARY)
    kotlin(Dependencies.Plugins.KOTLIN_JVM)
    kotlin(Dependencies.Plugins.KOTLIN_SERIALIZATION)
}

java {
    sourceCompatibility = AppConfig.JAVA_COMPATIBILITY
    targetCompatibility = AppConfig.JAVA_COMPATIBILITY
}

dependencies {
    // Coroutines
    implementation(Dependencies.ThirdParty.KOTLINX_COROUTINES_CORE)
    implementation(Dependencies.ThirdParty.KOTLINX_COROUTENS_ANDROID)

    implementation(Dependencies.ThirdParty.KOTLIN_SERIALIZATION)
}