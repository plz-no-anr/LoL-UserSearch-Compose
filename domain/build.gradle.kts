plugins {
    javaLib
    kotlin
    kotlinSerialization
}

java {
    sourceCompatibility = AppConfig.JAVA_COMPATIBILITY
    targetCompatibility = AppConfig.JAVA_COMPATIBILITY
}

dependencies {
    // Coroutines
    implementationCoroutines()
    implementation(Dependencies.ThirdParty.KOTLIN_SERIALIZATION)
}