plugins {
    this.kotlin("jvm") version "2.2.20"
    this.id("maven-publish")
    this.id("signing")
}

dependencies {
    this.testImplementation(kotlin("test"))
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
