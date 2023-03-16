plugins {
    id(Dependencies.Plugins.JAVA_LIBRARY)
    id(Dependencies.Plugins.KOTLIN_JVM)
}

java {
    sourceCompatibility = AppConfig.JAVA_COMPATIBILITY
    targetCompatibility = AppConfig.JAVA_COMPATIBILITY
}

dependencies {
    // Coroutines
    implementation(Dependencies.ThirdParty.KOTLINX_COROUTINES_CORE)
    implementation(Dependencies.ThirdParty.KOTLINX_COROUTENS_ANDROID)
}