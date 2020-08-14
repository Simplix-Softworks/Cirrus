package dev.simplix.cirrus.api.menu;

import java.util.Map;
import java.util.UUID;
import dev.simplix.cirrus.api.business.PlayerWrapper;

public interface MenuBuilder {

  <T> T build(T prebuild, Menu menu);
  <T> void open(PlayerWrapper playerWrapper, T inventoryImpl);

  Menu menuByHandle(Object handle);
  void destroyMenusOfPlayer(UUID uniqueId);
  Map.Entry<Menu, Long> lastBuildOfPlayer(UUID uniqueId);
  void invalidate(Menu menu);

}
