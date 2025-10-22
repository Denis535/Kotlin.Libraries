plugins {
    this.id("java-platform")
    this.id("maven-publish")
    this.id("signing")
}

subprojects {
    this.group = project.findProperty("project.group") as String
    this.version = project.findProperty("project.version") as String
    this.repositories {
        this.mavenCentral()
        this.maven {
            this.name = "GitHubPackages"
            this.url = uri("https://maven.pkg.github.com/Denis535/Kotlin.Libraries/")
            this.credentials {
                this.username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR") ?: error("GITHUB_ACTOR is not found")
                this.password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN") ?: error("GITHUB_TOKEN is not found")
            }
        }
    }
}
