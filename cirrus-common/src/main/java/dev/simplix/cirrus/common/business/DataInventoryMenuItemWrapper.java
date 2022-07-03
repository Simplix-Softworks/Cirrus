package dev.simplix.cirrus.common.business;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
@ToString(callSuper = true)
public class DataInventoryMenuItemWrapper<T> extends InventoryMenuItemWrapper {

    private final T data;

    public DataInventoryMenuItemWrapper(
            @NonNull MenuItemWrapper handle,
            @NonNull String actionHandler,
            @NonNull List<String> actionArguments, T data) {
        super(handle, actionHandler, actionArguments);
        this.data = data;
    }

}
