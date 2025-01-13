package com.plznoanr.lol

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

internal fun Project.configureKotestAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    configureKotest()
    configureJUnitAndroid(commonExtension)
}

internal fun Project.configureKotest() {
    configureJUnit()
    dependencies {
        "testImplementation"(libs.library("kotest-runner-junit5"))
        "testImplementation"(libs.library("kotest-assertions"))
    }
}

internal fun Project.configureJUnit() {
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

internal fun configureJUnitAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        testOptions {
            unitTests.all { it.useJUnitPlatform() }
        }
    }
}