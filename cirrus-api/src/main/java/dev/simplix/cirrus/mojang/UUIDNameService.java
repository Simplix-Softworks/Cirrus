package dev.simplix.cirrus.mojang;

import java.util.Optional;
import java.util.UUID;

/**
 * Class to get UUID's from Name and vice versa.
 */
public class UUIDNameService {

  public void saveUUIDAndName(UUID uuid, String name) {
    //UUID
//    StorageManager.getPlayerData().set(uuid.toString(), name);
  }

  public Optional<String> getName(UUID uuid) {

    //Not yet set
    //Getting from Mojang & Setting it manually
    String name = UUIDFetcher.getName(uuid);
    if (name == null) {
      return Optional.empty();
    }

//    StorageManager.getPlayerData().set(uuid.toString(), name);

    return Optional.of(name);
  }

  public Optional<UUID> getUUID(String name) {

//    for (val entry : StorageManager.getPlayerData().getFileData().toMap().entrySet()) {
//      if (entry.getValue().equals(name)) {
//        return UUID.fromString(entry.getKey());
//      }
//    }

    //Not yet set.
    //Getting from Mojang & Setting it manually.
    UUID uuid = UUIDFetcher.getUUID(name);

//    StorageManager.getPlayerData().set(uuid.toString(), name);
    return Optional.ofNullable(uuid);
  }

}