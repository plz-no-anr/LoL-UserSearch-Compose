
plugins {
    id(Dependencies.Plugin.application) version Versions.Plugin.agp apply false
    kotlin(Dependencies.Plugin.kotlin) version Versions.Plugin.kotlin apply false
    kotlin(Dependencies.Plugin.serialization) version Versions.Plugin.kotlin apply false
    id(Dependencies.Plugin.hilt) version Versions.Plugin.hilt apply false
    kotlin(Dependencies.Plugin.kapt) version Versions.Plugin.kotlin apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}