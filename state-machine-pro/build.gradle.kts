plugins {
    kotlin("jvm") version "2.2.20"
}

group = "com.denis535"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(24)
}

tasks.test {
    useJUnitPlatform()
}

//import org.jetbrains.kotlin.gradle.dsl.JvmTarget
//
//plugins {
//    kotlin("multiplatform") version "2.2.20"
//}
//
//group = "com.denis535"
//version = "1.0-SNAPSHOT"
//
//repositories {
//    mavenCentral()
//}

//kotlin {
//    jvm {
//        compilations.named("main") {
//            compileTaskProvider.configure {
//                compilerOptions {
//                    jvmTarget.set(JvmTarget.JVM_24)
//                }
//            }
//        }
//        testRuns["test"].executionTask.configure {
//            useJUnitPlatform()
//        }
//    }
//    jvmToolchain(24)
//    sourceSets {
//        val commonMain by getting
//        val commonTest by getting
//        val jvmMain by getting
//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test"))
//                implementation("org.junit.jupiter:junit-jupiter:5.10.0")
//            }
//        }
//    }
//}
