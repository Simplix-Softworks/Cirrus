import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectChecker.author

plugins {
    id("java")
    // https://plugins.gradle.org/plugin/dev.simplix.helper.bukkit
    id("dev.simplix.helper.bungee") version "1.0.4"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("xyz.jpenilla.run-waterfall") version "2.0.0"
    // Authenticated Maven publishing
    id("org.hibernate.build.maven-repo-auth") version "3.0.3"
}

group = "dev.simplix.cirrus"
version = "3.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Will only be used in "dev-mode"
bungee {
    main = "dev.simplix.cirrus.bungee.plugin.CirrusBungeePlugin"
    name = "Cirrus"
    version = "3.0.0"
    author = "SimplixSoft"
    depends = setOf("Protocolize")
}

// publish
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven("https://repo.simplix.dev/repository/simplixsoft-public/") {
            name = "simplixsoft-public"
        }
    }
}

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

tasks {

    assemble {
        dependsOn(shadowJar)
    }
    runWaterfall {
        waterfallVersion("1.19")
    }

    shadowJar {
    }
}



dependencies {

    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
    implementation(project(":cirrus-api"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}