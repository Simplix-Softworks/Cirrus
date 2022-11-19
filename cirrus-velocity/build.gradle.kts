plugins {
    id("java")
    // https://plugins.gradle.org/plugin/dev.simplix.helper.bukkit
    id("com.github.johnrengelman.shadow") version "7.1.2"
    // https://github.com/jpenilla/run-paper
    id("xyz.jpenilla.run-velocity") version "2.0.0"
    // Authenticated Maven publishing
    id("org.hibernate.build.maven-repo-auth") version "3.0.3"
}

group = "dev.simplix.cirrus"
version = "3.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://papermc.io/repo/repository/maven-public/")
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

dependencies {
    implementation(project(":cirrus-api"))
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks {
    runVelocity {
        velocityVersion("3.1.1")
    }

    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
    }
}
