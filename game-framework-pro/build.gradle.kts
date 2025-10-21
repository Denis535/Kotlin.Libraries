plugins {
    this.id("java-platform")
}

allprojects {
    this.group = this.findProperty("project.group") as String
    this.version = this.findProperty("project.version") as String
    this.repositories {
        this.mavenCentral()
    }
}

//publishing {
//    this.publications {
//        this.create<MavenPublication>("mavenJava") {
//            this.from(components["java"])
//            this.groupId = ProjectGroup
//            this.artifactId = ProjectName
//            this.version = ProjectVersion
//            this.pom {
//                this.name.set(ProjectName)
//                this.description.set(ProjectDescription)
//                this.url.set(ProjectUrl)
//                this.licenses {
//                    this.license {
//                        this.name.set("MIT License")
//                        this.url.set("https://opensource.org/licenses/MIT")
//                    }
//                }
//                this.developers {
//                    this.developer {
//                        this.id.set("denis535")
//                        this.name.set("Denis535")
//                    }
//                }
//                this.scm {
//                    this.connection.set("scm:git:git://https://github.com/Denis535/Kotlin.Libraries.git")
//                    this.developerConnection.set("scm:git:ssh://https://github.com/Denis535/Kotlin.Libraries.git")
//                    this.url.set("https://github.com/Denis535/Kotlin.Libraries/tree/main/game-framework-pro")
//                }
//            }
//        }
//    }
//}
//signing {
//    this.useInMemoryPgpKeys(
//        File("${projectDir}/0x32672C2E-sec.asc").readText(),
//        "qwerty"
//    )
//    this.sign(publishing.publications["mavenJava"])
//}
