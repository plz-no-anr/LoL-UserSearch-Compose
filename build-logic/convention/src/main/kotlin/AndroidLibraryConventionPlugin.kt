import com.android.build.gradle.LibraryExtension
import com.metaverse.world.cube.configureFlavors
import com.metaverse.world.cube.configureKotestAndroid
import com.metaverse.world.cube.configureKotlinAndroid
import com.metaverse.world.cube.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureKotestAndroid(this)
                defaultConfig.targetSdk = 34
                configureFlavors(this)
            }
            dependencies {
                add("testImplementation", libs.findLibrary("junit4").get())
            }
        }
    }
}