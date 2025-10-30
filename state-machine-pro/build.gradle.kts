plugins {
    this.kotlin("jvm") version "2.2.20"
    this.id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    this.id("maven-publish")
    this.id("signing")
}

group = project.group.toString()
version = project.version.toString()

dependencies {
    this.testImplementation(this.kotlin("test"))
}

repositories {
    this.mavenCentral()
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
            this.artifactId = project.name
            this.version = project.version.toString()
            this.pom {
                this.name = project.name
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
                    this.connection = "scm:git:git://https://github.com/Denis535/Kotlin.Libraries.git"
                    this.developerConnection = "scm:git:ssh://https://github.com/Denis535/Kotlin.Libraries.git"
                    this.url = "https://github.com/Denis535/Kotlin.Libraries"
                }
            }
        }
    }
//    this.repositories {
//        this.maven {
//            this.name = "GitHubPackages"
//            this.url = uri("https://maven.pkg.github.com/Denis535/Kotlin.Libraries")
//            this.credentials {
//                this.username = System.getenv("GITHUB_ACTOR")
//                this.password = System.getenv("GITHUB_TOKEN")
//            }
//        }
//    }
}

nexusPublishing {
    this.repositories {
        this.sonatype {
            this.username = System.getenv("SONATYPE_USERNAME")
            this.password = System.getenv("SONATYPE_PASSWORD")
        }
    }
}

signing {
    this.useInMemoryPgpKeys(
        File("${projectDir}/../0x32672C2E-sec.asc").readText(), "qwerty"
    )
    this.sign(publishing.publications["mavenJava"])
}
