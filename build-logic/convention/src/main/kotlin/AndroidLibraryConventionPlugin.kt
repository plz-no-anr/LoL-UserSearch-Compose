import com.android.build.gradle.LibraryExtension
import com.plznoanr.lol.configureFlavors
import com.plznoanr.lol.configureKotestAndroid
import com.plznoanr.lol.configureKotlinAndroid
import com.plznoanr.lol.library
import com.plznoanr.lol.libs
import com.plznoanr.lol.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugin("android-library").pluginId)

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureKotestAndroid(this)
                defaultConfig.targetSdk = 34
                configureFlavors(this)
            }
            dependencies {
                add("testImplementation", libs.library("junit4"))
            }
        }
    }
}