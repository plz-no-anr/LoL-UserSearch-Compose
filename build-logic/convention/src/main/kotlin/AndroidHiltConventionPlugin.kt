import com.plznoanr.lol.library
import com.plznoanr.lol.libs
import com.plznoanr.lol.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply(libs.plugin("hilt-android").pluginId)
                apply(libs.plugin("ksp").pluginId)
            }

            dependencies {
                "implementation"(libs.library("hilt-android"))
                "ksp"(libs.library("hilt-android-compiler"))
                "kspTest"(libs.library("hilt-android-compiler"))
                "kspAndroidTest"(libs.library("hilt-android-compiler"))
            }
        }
    }

}