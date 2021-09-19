package dev.simplix.cirrus.api.business;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
@ToString(callSuper = true)
public class DataInventoryItemWrapper<T> extends InventoryItemWrapper {

    private final T data;

    public DataInventoryItemWrapper(
            @NonNull ItemStackWrapper handle,
            @NonNull String actionHandler,
            @NonNull List<String> actionArguments, T data) {
        super(handle, actionHandler, actionArguments);
        this.data = data;
    }

}
