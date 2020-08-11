package dev.simplix.cirrus.api.business;

import java.util.UUID;

public interface PlayerWrapper {

  void sendMessage(String msg);

  void closeInventory();

  boolean hasPermission(String permission);

  UUID uniqueId();

  String name();

  int protocolVersion();

  <T> T handle();

}
