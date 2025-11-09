plugins {
    this.kotlin("jvm") version "2.2.21"
    this.id("maven-publish")
    this.id("signing")
}

dependencies {
    this.implementation(this.project(":main"))
    this.testImplementation(this.kotlin("test"))
}

java {
    this.withSourcesJar()
    this.withJavadocJar()
}

kotlin {
    this.jvmToolchain(21)
    this.compilerOptions {
        this.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
}

tasks.test {
    this.useJUnitPlatform()
}

publishing {
    this.publications {
        this.create<MavenPublication>("mavenJava") {
            this.from(components["java"])
            this.groupId = project.group.toString()
            this.artifactId = rootProject.name + "-" + project.name
            this.version = project.version.toString()
            this.pom {
                this.name = this@create.artifactId
                this.description = project.description
                this.url = project.findProperty("url").toString()
                this.licenses {
                    this.license {
                        this.name = "MIT License"
                        this.url = "https://opensource.org/licenses/MIT"
                    }
                }
                this.developers {
                    this.developer {
                        this.id = "denis535"
                        this.name = "Denis535"
                    }
                }
                this.scm {
                    this.connection = "scm:git:https://github.com/Denis535/Kotlin.Libraries.git"
                    this.developerConnection = "scm:git:ssh://git@github.com/Denis535/Kotlin.Libraries.git"
                    this.url = "https://github.com/Denis535/Kotlin.Libraries"
                }
            }
        }
    }
    this.repositories {
        this.maven {
            this.name = "GitHubPackages"
            this.url = uri("https://maven.pkg.github.com/Denis535/Kotlin.Libraries")
            this.credentials {
                this.username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR") ?: error("GITHUB_ACTOR is not found")
                this.password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN") ?: error("GITHUB_TOKEN is not found")
            }
        }
    }
}

signing {
    this.useInMemoryPgpKeys(
        File("${projectDir}/../../0x1F21E44D-sec.asc").readText(), "qwerty"
    )
    this.sign(publishing.publications["mavenJava"])
}
