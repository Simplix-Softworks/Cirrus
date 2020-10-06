package dev.simplix.cirrus.api.business;

import java.util.UUID;
import lombok.NonNull;

public interface PlayerWrapper {

  void sendMessage(@NonNull String msg);

  void closeInventory();

  boolean hasPermission(@NonNull String permission);

  UUID uniqueId();

  String name();

  int protocolVersion();

  <T> T handle();

}
