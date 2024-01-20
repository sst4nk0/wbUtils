plugins {
    java
    idea
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.github.sst4nk0"
version = "0.09"
description = "wbUtils"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/public/") }
}

dependencies {
    paperweight.paperDevBundle("1.19.3-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.5")
    compileOnly("com.github.emanondev:ItemEdit:2.20")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.2") {
        exclude(group = "com.github.waffle", module = "waffle-jna")
    }
    implementation("com.zaxxer:HikariCP:5.1.0")
    testCompileOnly("org.testng:testng:7.9.0")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
        append("META-INF/provides")

        configurations = listOf(project.configurations.runtimeClasspath.get())
    }

    build {
        dependsOn(shadowJar)
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    named<Test>("test") {
        useTestNG()
    }
}
