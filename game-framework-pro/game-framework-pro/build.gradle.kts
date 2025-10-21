plugins {
    kotlin("jvm")
    id("maven-publish")
}

dependencies {
    testImplementation(kotlin("test"))
}

java { withSourcesJar() }

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "game-framework-pro"
        }
    }
}
