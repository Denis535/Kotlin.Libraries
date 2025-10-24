plugins {
    this.kotlin("jvm") version "2.2.20"
    this.id("maven-publish")
    this.id("signing")
}

dependencies {
    this.api("io.github.denis535:state-machine-pro:1.0.2")
    this.api("io.github.denis535:tree-machine-pro:1.0.2")
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

//publishing {
//    this.publications {
//        this.create<MavenPublication>("mavenJava") {
//            this.from(components["java"])
//            this.groupId = findProperty("project.group") as String
//            this.artifactId = findProperty("project.name") as String
//            this.version = findProperty("project.version") as String
//            this.pom {
//                this.name = findProperty("project.name") as String
//                this.description = findProperty("project.description") as String
//                this.url = findProperty("project.url") as String
//                this.licenses {
//                    this.license {
//                        this.name = "MIT License"
//                        this.url = "https://opensource.org/licenses/MIT"
//                    }
//                }
//                this.developers {
//                    this.developer {
//                        this.id = "denis535"
//                        this.name = "Denis535"
//                    }
//                }
//                this.scm {
//                    this.connection = "scm:git:git://https://github.com/Denis535/Kotlin.Libraries.git"
//                    this.developerConnection = "scm:git:ssh://https://github.com/Denis535/Kotlin.Libraries.git"
//                    this.url = "https://github.com/Denis535/Kotlin.Libraries"
//                }
//            }
//        }
//    }
//}

//signing {
//    this.useInMemoryPgpKeys(
//        File("${projectDir}/../0x32672C2E-sec.asc").readText(), "qwerty"
//    )
//    this.sign(publishing.publications["mavenJava"])
//}
