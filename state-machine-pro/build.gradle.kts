plugins {
    kotlin("jvm") version "2.2.20"
    id("maven-publish")
}

group = "com.denis535"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

java {
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(24)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group.toString()
            artifactId = project.name
            version = version.toString()
        }
    }
    repositories {
        mavenLocal()
    }
}
