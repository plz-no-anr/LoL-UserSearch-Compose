plugins {
    javaLib
    kotlin
    kotlinSerialization
}

java {
    sourceCompatibility = AppConfig.javaCompatibility
    targetCompatibility = AppConfig.javaCompatibility
}

kotlin {
}

dependencies {
    // Coroutines
    implementation(Dependencies.ThirdParty.coroutinesCore)
    implementation(Dependencies.ThirdParty.kotlinSerialization)
}