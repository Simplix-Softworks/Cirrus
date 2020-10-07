package dev.simplix.cirrus.bungeecord.example.menus;

import dev.simplix.cirrus.api.business.DataInventoryItemWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.model.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.prefabs.menu.AbstractAsyncFetchMultiPageMenu;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executors;
import lombok.NonNull;

public class ExampleFetchInventory extends AbstractAsyncFetchMultiPageMenu<UUID> {

  public ExampleFetchInventory(
      @NonNull PlayerWrapper player,
      @NonNull MultiPageMenuConfiguration configuration) {
    super(player, configuration, Locale.ENGLISH, Executors.newSingleThreadExecutor());
  }

  @Override
  public List<DataInventoryItemWrapper<UUID>> fetch(
      int from, int to) {
    return Collections.singletonList(new DataInventoryItemWrapper<>(
        null,
        "noAction",
        Collections.emptyList(),
        UUID.randomUUID()));
  }

}
