plugins {
    id("java")
    // https://github.com/johnrengelman/shadow
    id("com.github.johnrengelman.shadow") version "7.0.0"
    // https://github.com/jpenilla/run-paper
    id("xyz.jpenilla.run-paper") version "2.0.0"
    // https://plugins.gradle.org/plugin/dev.simplix.helper.bukkit
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    // Authenticated Maven publishing
    id("org.hibernate.build.maven-repo-auth") version "3.0.3"
}

group = "dev.simplix.cirrus"
version = "3.0.0-SNAPSHOT"

// Will only be used in "dev-mode"
bukkit {
    main = "dev.simplix.cirrus.spigot.plugin.CirrusSpigotPlugin"
    name = "CirrusSpigot"
    version = "3.0.0"
    author = "SimplixSoft"
    apiVersion = "1.16"
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
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    implementation(project(":cirrus-api"))
    implementation("dev.simplix:protocolize-api:2.2.2") // Needs to be shaded in this case
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    runServer {
        minecraftVersion("1.17.1")
    }

    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        exclude("**/registries/**")
        relocate("net.querz", "dev.simplix.cirrus.spigot.lib")
        relocate(
                "dev.simplix.protocolize",
                "dev.simplix.cirrus.spigot.lib.protocolize")
    }

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}