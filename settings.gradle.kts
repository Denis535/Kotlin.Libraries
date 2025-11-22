plugins {
    this.id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "Kotlin.Libraries"

include("state-machine-pro", "tree-machine-pro", "game-framework-pro")
