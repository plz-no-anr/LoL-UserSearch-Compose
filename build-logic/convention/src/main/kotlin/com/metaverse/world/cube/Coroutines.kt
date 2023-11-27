package com.metaverse.world.cube

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutineAndroid() {
    configureCoroutineKotlin()
    dependencies {
        "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
    }
}

internal fun Project.configureCoroutineKotlin() {
    dependencies {
        "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())
        "testImplementation"(libs.findLibrary("kotlinx.coroutines.test").get())
    }
}
