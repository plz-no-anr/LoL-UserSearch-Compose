package com.plznoanr.lol

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures.compose = true


        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }

        tasks.withType<KotlinCompile>().configureEach {
            buildComposeMetricsParameters().forEach {
                compilerOptions.freeCompilerArgs.add(it)
            }
        }
    }

}

/**
 *  Command line parameters to enable Compose compiler metrics and reports.
 *  ./gradlew assembleDev -PcomposeCompilerMetrics=true -PcomposeCompilerReports=true
 */
private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters = mutableListOf<String>()
    val relativePath = projectDir.relativeTo(rootDir)
    val buildDir = layout.buildDirectory.get().asFile
    if (project.findProperty("composeCompilerReports") == "true") {
        metricParameters += arrayOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${
                buildDir.resolve("compose-metrics").resolve(relativePath)
            }"
        )
    }

    if (project.findProperty("composeCompilerMetrics") == "true") {
        metricParameters += arrayOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${
                buildDir.resolve("compose-reports").resolve(relativePath)
            }"
        )
    }
    return metricParameters.toList()
}