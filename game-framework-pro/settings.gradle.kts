plugins {
    this.id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "game-framework-pro"

dependencyResolutionManagement {
    this.repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    this.repositories {
        this.mavenCentral()
        this.maven {
            this.url = uri("https://jitpack.io")
        }
    }
}

include("main")
include("extensions")
