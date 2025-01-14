import com.plznoanr.lol.library
import com.plznoanr.lol.libs
import com.plznoanr.lol.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugin("kotlin-serialization").pluginId)
            dependencies {
                add("implementation", libs.library("kotlinx-serialization-json"))
            }
        }
    }
}