plugins {
    kotlin("jvm") version "2.2.20"
    id("maven-publish")
    id("signing")
}

dependencies {
    this.testImplementation(kotlin("test"))
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
