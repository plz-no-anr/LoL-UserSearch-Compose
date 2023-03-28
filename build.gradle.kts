// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.ClassPath.GRADLE)
//        classpath(Dependencies.ClassPath.KOTLIN_PLUGIN)
        classpath(Dependencies.ClassPath.HILT_PLUGIN)
        classpath(kotlin(Dependencies.ClassPath.KOTLIN_GRADLE, version = Dependencies.ClassPath.Version.KOTLIN_VERSION))
        classpath(kotlin(Dependencies.ClassPath.SERIALIZATION, version = Dependencies.ClassPath.Version.KOTLIN_VERSION))
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}