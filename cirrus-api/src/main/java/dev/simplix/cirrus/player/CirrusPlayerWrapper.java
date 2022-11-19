package dev.simplix.cirrus.player;

import java.util.UUID;

public interface CirrusPlayerWrapper {
  UUID uuid();
  <T> T handle();

  int protocolVersion();

  void sendMessage(String message);
}
