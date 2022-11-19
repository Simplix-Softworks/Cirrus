rootProject.name = "cirrus"
include("cirrus-api")
include("cirrus-spigot")
include("cirrus-bungeecord")
include("cirrus-bungeecord:src:main:cirrus-velocity")
findProject(":cirrus-bungeecord:src:main:cirrus-velocity")?.name = "cirrus-velocity"
include("cirrus-spigot")
include("cirrus-bungeecord")
include("cirrus-velocity")
