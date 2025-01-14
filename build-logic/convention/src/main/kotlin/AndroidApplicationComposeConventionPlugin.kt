import com.android.build.api.dsl.ApplicationExtension
import com.plznoanr.lol.configureAndroidCompose
import com.plznoanr.lol.libs
import com.plznoanr.lol.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply(libs.plugin("android-application").pluginId)
                apply(libs.plugin("compose-compiler").pluginId)
                apply(libs.plugin("compose-compiler-report").pluginId)
            }

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }

}