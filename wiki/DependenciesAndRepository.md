Version: Get the latest 3.0.0 snapshot version by using: <p>
`3.0.0-SNAPSHOT`

# Repository:
`https://repo.simplix.dev/repository/simplixsoft-public/`

# Dependency

| Platform   | Supported?                                   |
|------------|----------------------------------------------|
| API        | dev.simplix.cirrus:cirrus-api:VERSION        |
| Spigot     | dev.simplix.cirrus:cirrus-spigot:VERSION     |
| BungeeCord | dev.simplix.cirrus:cirrus-bungeecord:VERSION |
| Velocity   | dev.simplix.cirrus:cirrus-velocity:VERSION   |

# Gradle

```kotlin
repositories {
    maven("https://repo.simplix.dev/repository/simplixsoft-public/")
}
implementation("dev.simplix.cirrus:cirrus-PLATFORM:VERSION")
```

# Maven

```xml
<repositories>
    <repository>
        <id>simplixsoft-public</id>
        <url>https://repo.simplix.dev/repository/simplixsoft-public/</url>
    </repository>
</repositories>
<dependency>
  <groupId>dev.simplix.cirrus</groupId>
  <artifactId>cirrus-PLATFORM</artifactId>
  <version>VERSION</version>
</dependency>
```

