// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Plugin.gradle)
        classpath(Dependencies.Plugin.hilt)
        classpath(kotlin(Dependencies.Plugin.kotlinGradle, version = Version.Plugin.kotlin))
        classpath(kotlin(Dependencies.Plugin.serialization, version = Version.Plugin.kotlin))
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}