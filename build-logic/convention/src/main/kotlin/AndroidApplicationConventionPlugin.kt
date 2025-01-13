import com.android.build.api.dsl.ApplicationExtension
import com.plznoanr.lol.configureKotlinAndroid
import com.plznoanr.lol.libs
import com.plznoanr.lol.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply(libs.plugin("android-application").pluginId)
                apply(libs.plugin("kotlin-android").pluginId)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
            }
        }
    }

}