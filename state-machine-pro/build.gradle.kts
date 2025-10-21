val ProjectGroup = "io.github.denis535"
val ProjectName = project.name
val ProjectVersion = "1.0.0"
val ProjectDescription = "The library that allows you to easily implement a stateful object."
val ProjectUrl = "https://github.com/Denis535/Kotlin.Libraries/tree/main/state-machine-pro"

plugins {
    this.kotlin("jvm") version "2.2.20"
    this.id("maven-publish")
    this.id("signing")
}

group = ProjectGroup
version = ProjectVersion

repositories {
    this.mavenCentral()
}

dependencies {
    this.testImplementation(this.kotlin("test"))
}

java {
    this.withSourcesJar()
    this.withJavadocJar()
}

kotlin {
    this.jvmToolchain(24)
}

tasks.test {
    this.useJUnitPlatform()
}

publishing {
    this.publications {
        this.create<MavenPublication>("mavenJava") {
            this.from(components["java"])
            this.groupId = ProjectGroup
            this.artifactId = ProjectName
            this.version = ProjectVersion
            this.pom {
                this.name.set(ProjectName)
                this.description.set(ProjectDescription)
                this.url.set(ProjectUrl)
                this.licenses {
                    this.license {
                        this.name.set("MIT License")
                        this.url.set("https://opensource.org/licenses/MIT")
                    }
                }
                this.developers {
                    this.developer {
                        this.id.set("denis535")
                        this.name.set("Denis535")
                    }
                }
                this.scm {
                    this.connection.set("scm:git:git://https://github.com/Denis535/Kotlin.Libraries.git")
                    this.developerConnection.set("scm:git:ssh://https://github.com/Denis535/Kotlin.Libraries.git")
                    this.url.set("https://github.com/Denis535/Kotlin.Libraries/tree/main/state-machine-pro")
                }
            }
        }
    }
//    this.repositories {
//        this.maven {
//            this.name = "sonatype"
//            this.url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
//            this.credentials {
//                this.username = project.findProperty("ossrhUsername") as String?
//                this.password = project.findProperty("ossrhPassword") as String?
//            }
//        }
//    }
}

signing {
    this.useInMemoryPgpKeys(
        File("${projectDir}/0x32672C2E-sec.asc").readText(),
        "qwerty"
    )
    this.sign(publishing.publications["mavenJava"])
}
