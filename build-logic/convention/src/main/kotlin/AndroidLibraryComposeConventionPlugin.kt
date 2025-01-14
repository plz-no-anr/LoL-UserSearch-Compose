import com.plznoanr.lol.configureAndroidCompose
import com.plznoanr.lol.libraryExtension
import com.plznoanr.lol.libs
import com.plznoanr.lol.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply(libs.plugin("android-library").pluginId)
                apply(libs.plugin("compose-compiler").pluginId)
            }
            configureAndroidCompose(libraryExtension)
        }
    }

}