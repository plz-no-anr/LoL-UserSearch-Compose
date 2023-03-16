import org.gradle.api.JavaVersion

object AppConfig {

    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 23
    const val TARGET_SDK_VERSION = 33
    const val APPLICATION_ID = "com.plznoanr.lol_usersearch_compose"
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    const val ANDROID_TEST_INSTRUMENTATION = "androidx.test.runner.AndroidJUnitRunner"

    val JAVA_JVM_TARGET = JavaVersion.VERSION_11.toString()
    val JAVA_COMPATIBILITY = JavaVersion.VERSION_11

    const val KOTLIN_COMPILER_EXTENTION = "1.4.2"

}