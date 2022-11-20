package dev.simplix.cirrus.player;

import dev.simplix.cirrus.model.SimpleSound;
import java.util.UUID;

public interface CirrusPlayerWrapper {

  UUID uuid();

  <T> T handle();

  int protocolVersion();

  void play(SimpleSound sound);

  void sendMessage(String message);
}
