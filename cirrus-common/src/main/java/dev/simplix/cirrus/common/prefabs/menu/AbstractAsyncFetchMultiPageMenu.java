package dev.simplix.cirrus.common.prefabs.menu;

import dev.simplix.cirrus.api.business.DataInventoryItemWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.CallResult;
import dev.simplix.cirrus.api.model.MultiPageMenuConfiguration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public abstract class AbstractAsyncFetchMultiPageMenu<T> extends MultiPageMenu {

  private final ExecutorService executorService;
  private volatile boolean fetching;
  @Setter
  private boolean fetchPaginated = true;
  private int fetchIndex;

  public AbstractAsyncFetchMultiPageMenu(
      @NonNull PlayerWrapper player,
      @NonNull MultiPageMenuConfiguration configuration,
      @NonNull Locale locale,
      @NonNull ExecutorService executorService) {
    super(player, configuration, locale);
    this.executorService = executorService;

    registerActionHandler("nextPage", click -> {
      if (pages().size() == currentPageNumber()) {
        return CallResult.DENY_GRABBING;
      }
      if (fetching) {
        return CallResult.DENY_GRABBING;
      }
      fetching = true;
      executorService.execute(() -> errorProne(() -> {
        int pageNumber = currentPageNumber();
        if (fetchPaginated) {
          fetch().forEach(this::add);
        }
        currentPage(pageNumber + 1);
        build();
      }, () -> fetching = false));
      return CallResult.DENY_GRABBING;
    });

    refetch();
  }

  public void refetch() {
    if (fetching) {
      return;
    }
    currentPage(1);
    fetching = true;
    fetchIndex = 0;
    executorService.execute(() -> errorProne(
        () -> fetch().forEach(this::add),
        () -> fetching = false));
  }

  private List<DataInventoryItemWrapper<T>> fetch() {
    int start = fetchIndex;
    List<DataInventoryItemWrapper<T>> fetch = fetch(start, fetchIndex + countEmptySlots() + 1);
    fetchIndex += fetch.size();
    return fetch;
  }

  private int countEmptySlots() {
    int i = -1;
    int out = -1;
    while ((i = topContainer().nextFreeSlot(i)) != -1) {
      out = i;
    }
    return out;
  }

  public abstract List<DataInventoryItemWrapper<T>> fetch(int from, int to);

}
