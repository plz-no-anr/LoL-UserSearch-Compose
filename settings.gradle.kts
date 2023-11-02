pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "lol-user-search-compose-mvi"
include(":app")
include(":core:common")
include(":core:network")
include(":core:database")
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:datastore")