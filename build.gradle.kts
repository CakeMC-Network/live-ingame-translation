import java.net.URI
import java.nio.charset.StandardCharsets
import java.util.*

plugins {
    kotlin("jvm") version "2.0.0"
}

group = "net.cakemc.util"
version = "0.0.0-develop"

val jdkVersion = JavaVersion.VERSION_21
val jdkVersionString = jdkVersion.toString()

val repoProperties = Properties()
val repoFile = file("/credentials.properties")
if (repoFile.exists())
    repoProperties.load(repoFile.inputStream())
var repoUsername = repoProperties.getProperty("username", System.getenv("REPOSITORY_USERNAME"))
var repoPassword = repoProperties.getProperty("password", System.getenv("REPOSITORY_PASSWORD"))

repositories {
    mavenLocal()
    mavenCentral()

    maven {
        name = "cakemc-nexus"
        url = URI.create("http://cakemc.net:8081/releases")
        credentials {
            username = repoUsername
            password = repoPassword
        }
        isAllowInsecureProtocol = true
    }
}

dependencies {
    implementation(
        group = "net.cakemc.cluster",
        name = "cerberus-api",
        version = "0.0.0-develop",
        classifier = "all"
    )
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(jdkVersionString)
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = jdkVersionString
    targetCompatibility = jdkVersionString
    options.encoding = StandardCharsets.UTF_8.toString()
}

tasks.withType<AbstractArchiveTask> {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}
