import com.plznoanr.lol.configureKotlinJvm
import com.plznoanr.lol.libs
import com.plznoanr.lol.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugin("kotlin-jvm").pluginId)
            configureKotlinJvm()
        }
    }
}
