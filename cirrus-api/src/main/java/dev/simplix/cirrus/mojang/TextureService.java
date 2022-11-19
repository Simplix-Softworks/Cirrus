package dev.simplix.cirrus.mojang;

import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 * Provider to get the Skull-Texture of a player as base 64 hash.
 */
@Slf4j
public class TextureService {

  public void saveSkinTexture(final UUID uuid, final String name) {
    //Skin Texture
//    final String skinTexture = getSkinTexture(uuid);
//    if (skinTexture!=null && !skinTexture.isEmpty()) {
//      StorageManager.getTextureCache().set(uuid.toString(), skinTexture);
//    }
  }

  public Optional<String> skinTexture(final UUID uuid) {

//    if (StorageManager.getTextureCache().contains(uuid.toString())) {
//      return StorageManager.getTextureCache().getString(uuid.toString());
//    }

    final String hash = MojangTextureFetcher.getTextureHash(uuid);
    log.debug("Received hash '" + hash + "'");

    if (hash == null || hash.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(hash);
  }
}