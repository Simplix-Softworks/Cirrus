package dev.simplix.cirrus.api.business;

import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class DataInventoryItemWrapper<T> extends InventoryItemWrapper {

  private final T data;

  DataInventoryItemWrapper(
      ItemStackWrapper handle,
      String actionHandler,
      List<String> actionArguments, T data) {
    super(handle, actionHandler, actionArguments);
    this.data = data;
  }

}
