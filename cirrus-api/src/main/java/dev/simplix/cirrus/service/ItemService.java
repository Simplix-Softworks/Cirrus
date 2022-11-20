package dev.simplix.cirrus.service;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.data.ItemType;

public class ItemService {

  public boolean isItemAvailable(ItemType itemType, int protocolVersion) {
    return Protocolize.mappingProvider().mapping(itemType, protocolVersion) != null;
  }
}
