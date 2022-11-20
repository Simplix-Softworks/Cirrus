plugins {
    id("java")
}

group = "dev.simplix.cirrus"
version = "3.0.0-SNAPSHOT"

allprojects {
    apply(plugin = "java") // Needed to set javac arguments
    apply(plugin = "maven-publish")
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven(url = "https://libraries.minecraft.net")
        maven(url = "https://jitpack.io")
        maven(url = "https://repo.simplix.dev/repository/simplixsoft-public/")
        maven(url = "https://mvn.exceptionflug.de/repository/exceptionflug-public/")
    }

    sourceSets {
        main {
            java {

                if (System.getenv()["simplix.dev"] == "on") {
                    return@java
                }
                println("Removing plugin-specifics for ${project.name}")
                exclude("")
//                exclude("dev/simplix/cirrus/menus/example/**")
                exclude("dev/simplix/cirrus/spigot/plugin/CirrusSpigotPlugin.java")
                exclude("dev/simplix/cirrus/bungee/plugin/CirrusBungeePlugin/**")
                exclude("dev/simplix/cirrus/velocity/plugin/CirrusVelocityPlugin/**")
            }
            resources {
                if (System.getenv()["simplix.dev"] == "on") {
                    return@resources
                }
                exclude { file ->
                    file.name.endsWith(".yml")
                }
            }
        }
    }

    dependencies {
        lib("org.projectlombok:lombok:1.18.24")
        lib("com.google.code.gson:gson:2.10")
        lib("org.slf4j:slf4j-api:1.8.0-beta4")

        lib("dev.simplix:protocolize-api:2.2.2")
        lib("com.mojang:authlib:1.5.21")

        annotationProcessor("org.projectlombok:lombok:1.18.24")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
        testCompileOnly("org.projectlombok:lombok:1.18.24")
        testImplementation("org.slf4j:slf4j-api:1.8.0-beta4")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

//

fun DependencyHandlerScope.lib(value: String) {
    compileOnly(value)
    testImplementation(value)
}