plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    val junitVersion = "5.10.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

sourceSets {
    /*main {
        kotlin.srcDir("src")
    }*/
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
