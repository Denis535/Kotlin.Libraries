plugins {
    this.id("java-platform")
}

subprojects {
    this.group = this.project.findProperty("project.group") as String
    this.version = this.project.findProperty("project.version") as String
    this.repositories {
        this.mavenCentral()
        this.maven {
            this.name = "GitHubPackages"
            this.url = this@subprojects.uri("https://maven.pkg.github.com/Denis535/Kotlin.Libraries/")
            this.credentials {
                this.username = this@subprojects.project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR") ?: error("GITHUB_ACTOR is not found")
                this.password = this@subprojects.project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN") ?: error("GITHUB_TOKEN is not found")
            }
        }
    }
    this.afterEvaluate {
        this.tasks.withType<Jar> {
            this.archiveBaseName = this@afterEvaluate.rootProject.name + "-" + this@afterEvaluate.project.name
        }
    }
}
