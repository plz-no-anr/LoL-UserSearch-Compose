package com.plznoanr.lol

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutineAndroid() {
    configureCoroutineKotlin()
    dependencies {
        "implementation"(libs.library("kotlinx-coroutines-android"))
    }
}

internal fun Project.configureCoroutineKotlin() {
    dependencies {
        "implementation"(libs.library("kotlinx-coroutines-core"))
        "testImplementation"(libs.library("kotlinx-coroutines-test"))
    }
}
