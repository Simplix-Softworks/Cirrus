package dev.simplix.cirrus.model;

import dev.simplix.cirrus.item.CirrusItem;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.*;
import javax.annotation.Nullable;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class BusinessItemMap implements ConcurrentMap<String, CirrusItem> {

  public BusinessItemMap(Map<String, CirrusItem> items) {
    this.value.putAll(items);
  }

  private final Map<String, CirrusItem> value = new ConcurrentHashMap<>();

  // Delegate methods

  @Override
  public int size() {
    return this.value.size();
  }

  @Override
  public boolean isEmpty() {
    return this.value.isEmpty();
  }

  @Override
  public boolean containsKey(@NonNull Object key) {
    return this.value.containsKey(key);
  }

  @Override
  public boolean containsValue(@NonNull Object value) {
    return this.value.containsValue(value);
  }

  @Override
  public CirrusItem get(@NonNull Object key) {
    return this.value.get(key);
  }

  @Nullable
  @Override
  public CirrusItem put(@NonNull String key, @NonNull CirrusItem value) {
    return this.value.put(key, value);
  }

  @Override
  public CirrusItem remove(Object key) {
    return this.value.remove(key);
  }

  @Override
  public void putAll(@NonNull Map<? extends String, ? extends CirrusItem> m) {
    this.value.putAll(m);
  }

  @Override
  public void clear() {
    this.value.clear();
  }

  @NonNull
  @Override
  public Set<String> keySet() {
    return this.value.keySet();
  }

  @NonNull
  @Override
  public Collection<CirrusItem> values() {
    return this.value.values();
  }

  @NonNull
  @Override
  public Set<Entry<String, CirrusItem>> entrySet() {
    return this.value.entrySet();
  }

  @Override
  public boolean equals(Object obj) {
    return this.value.equals(obj);
  }

  @Override
  public int hashCode() {
    return this.value.hashCode();
  }

  @Override
  public CirrusItem getOrDefault(Object key, CirrusItem defaultValue) {
    return this.value.getOrDefault(key, defaultValue);
  }

  @Override
  public void forEach(BiConsumer<? super String, ? super CirrusItem> action) {
    this.value.forEach(action);
  }

  @Override
  public void replaceAll(BiFunction<? super String, ? super CirrusItem, ? extends CirrusItem> function) {
    this.value.replaceAll(function);
  }

  @Override
  public CirrusItem putIfAbsent(String key, CirrusItem value) {
    return this.value.putIfAbsent(key, value);
  }

  @Override
  public boolean remove(Object key, Object value) {
    return this.value.remove(key, value);
  }

  @Override
  public boolean replace(
          String key,
          CirrusItem oldValue,
          CirrusItem newValue) {
    return this.value.replace(key, oldValue, newValue);
  }

  @Override
  public CirrusItem replace(String key, CirrusItem value) {
    return this.value.replace(key, value);
  }

  @Override
  public CirrusItem computeIfAbsent(
          String key,
          @NonNull Function<? super String, ? extends CirrusItem> mappingFunction) {
    return this.value.computeIfAbsent(key, mappingFunction);
  }

  @Override
  public CirrusItem computeIfPresent(
          String key,
          @NonNull BiFunction<? super String, ? super CirrusItem, ? extends CirrusItem> remappingFunction) {
    return this.value.computeIfPresent(key, remappingFunction);
  }

  @Override
  public CirrusItem compute(
          String key,
          @NonNull BiFunction<? super String, ? super CirrusItem, ? extends CirrusItem> remappingFunction) {
    return this.value.compute(key, remappingFunction);
  }

  @Override
  public CirrusItem merge(
          String key,
          @NonNull CirrusItem value,
          @NonNull BiFunction<? super CirrusItem, ? super CirrusItem, ? extends CirrusItem> remappingFunction) {
    return this.value.merge(key, value, remappingFunction);
  }

  @Override
  public String toString() {
    return Objects.toString(this.value);
  }

  public BusinessItemMap copy() {
    return new BusinessItemMap(this.value);
  }
}
