package dev.simplix.cirrus.menu;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuUpdateTask implements Runnable {

  private static final Map<Long, Long> idUpdateMillisMap = new ConcurrentHashMap<>();

  @Override
  public void run() {
    try {
      Menus.all().forEach(this::run);
    } catch (Exception exception) {
      log.error("Could not run update task", exception);
    }
  }

  private void run(DisplayedMenu menu) {
    try {
      run0(menu);
    } catch (Exception exception) {
      log.error("Could not update menu with id: " + menu.id(), exception);
    }
  }

  /**
   * 20 ticks = 1s = 1000ms
   * 1 tick = 50ms
   * 1ms = 0,02ticks .
   */

  private void run0(DisplayedMenu menu) {
    int timeBetweenUpdatesInTicks = menu.value().updateTicks();
    if (timeBetweenUpdatesInTicks < 0) {
      return;
    }
    if (menu.closed().get()) {
      return;
    }
    // One tick = 50ms
    long timeBetweenUpdatesInMS = timeBetweenUpdatesInTicks * 50L;

    long lastUpdateMillis = idUpdateMillisMap.getOrDefault(menu.id(), 0L);
    long nextUpdateMillis = lastUpdateMillis + timeBetweenUpdatesInMS;

    if (nextUpdateMillis > System.currentTimeMillis()) {
      return;
    }
    menu.update(); // Rebuild
    idUpdateMillisMap.put(menu.id(), System.currentTimeMillis());
  }
}