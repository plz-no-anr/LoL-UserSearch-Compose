import org.gradle.api.JavaVersion

object AppConfig {

    const val applicationId = "com.plz.no.anr.lol"
    const val compileSdkVersion = 33
    const val minSdkVersion = 23
    const val targetSdkVersion = 33
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"

    val jvmTarget = JavaVersion.VERSION_17.toString()
    val javaCompatibility = JavaVersion.VERSION_17

    const val kotlinCompilerExtention = "1.4.7"

    const val proguardFileName = "proguard-android-optimize.txt"
    const val proguardRules = "proguard-rules.pro"
}

object NameSpace {
    const val data = "com.plz.no.anr.lol.data"
}