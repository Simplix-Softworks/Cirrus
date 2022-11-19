package dev.simplix.cirrus.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.simplix.cirrus.menu.Menu;
import java.util.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Variables {

  private final Table<Class<Menu>, String, String> variables = HashBasedTable.create();
  private final Map<String, String> global = new HashMap<>();
  private boolean isEnabled = true;

  public Map<String, String> allFor(Class<Menu> clazz) {
    final HashMap<String, String> result = new HashMap<>(global());
    result.putAll(variables.row(clazz));
    return result;
  }

  public void remove(Class<Menu> clazz, String key) {
    variables.remove(clazz, key);
  }

  public Map<String, String> global() {
    return Collections.unmodifiableMap(global);
  }

  public void registerReplacement(String key, String value) {
    global.put(key, value);
  }

  public void registerReplacement(Class<Menu> clazz, String key, String value) {
    variables.put(clazz, key, value);
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void isEnabled(boolean isEnabled) {
    Variables.isEnabled = isEnabled;
  }

}
