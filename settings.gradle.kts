rootProject.name = "LoL-UserSearch-Compose"
include (":app", ":data", ":domain")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

