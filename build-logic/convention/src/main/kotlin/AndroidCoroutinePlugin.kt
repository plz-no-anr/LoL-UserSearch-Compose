import com.metaverse.world.cube.configureCoroutineAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidCoroutinePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureCoroutineAndroid()
        }
    }
}