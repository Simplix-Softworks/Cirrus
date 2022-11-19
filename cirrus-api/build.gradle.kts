plugins {
  id("java")
  application
}

group = "dev.simplix.cirrus"
version = "3.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
    }
  }
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}
