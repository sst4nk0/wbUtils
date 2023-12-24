plugins {
    java
    idea
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "wb.plugin"
version = "0.09"
description = "wbUtils"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
    maven { url = uri("https://repo.maven.apache.org/maven2/") }
}

//dependencies {
//    api(libs.org.testng.testng)
//    compileOnly(libs.me.clip.placeholderapi)
//    compileOnly(libs.com.github.emanondev.itemedit)
//    compileOnly(libs.io.papermc.paper.paper.api)
//}

dependencies {
    compileOnly("me.clip:placeholderapi:2.11.2")
    compileOnly("com.github.emanondev:ItemEdit:2.20")
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    api("org.testng:testng:7.8.0")
}

//publishing {
//    publications.create<MavenPublication>("maven") {
//        from(components["java"])
//    }
//}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.named<Test>("test") {
    useTestNG()
}
