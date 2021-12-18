package dev.simplix.cirrus.common.business;

import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

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
