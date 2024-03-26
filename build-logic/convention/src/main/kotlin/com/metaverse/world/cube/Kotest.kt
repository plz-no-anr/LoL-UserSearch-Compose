package com.metaverse.world.cube

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
        "testImplementation"(libs.findLibrary("kotest.runner.junit5").get())
        "testImplementation"(libs.findLibrary("kotest.assertions").get())
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