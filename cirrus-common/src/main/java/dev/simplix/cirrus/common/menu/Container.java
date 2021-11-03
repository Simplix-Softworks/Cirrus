package dev.simplix.cirrus.common.menu;

import dev.simplix.cirrus.common.business.InventoryItemWrapper;
import dev.simplix.cirrus.common.business.ItemStackWrapper;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A container stores {@link InventoryItemWrapper}s at a given slot position.
 */
public interface Container {

    /**
     * Returns a map collecting all items.
     *
     * @return The map
     */
    Map<Integer, InventoryItemWrapper> itemMap();

    /**
     * Returns a set with slots that are marked as reserved
     *
     * @return The set
     */
    Set<Integer> reservedSlots();

    /**
     * Returns the maximum capacity of this container
     *
     * @return the maximum amount of items this container can hold
     */
    int capacity();

    /**
     * Returns the initial slot id of this container
     *
     * @return Initial slot id
     */
    int baseSlot();

    /**
     * @return the slot index of the next available empty slot or -1 if no slot is empty
     */
    int nextFreeSlot();

    /**
     * @return the slot index of the next available empty slot or -1 after the specified index
     */
    int nextFreeSlot(int i);

    /**
     * Sets an item into the next free slot. Free slots are slots where no item has been set and the
     * slot is not marked as reserved.
     *
     * @param inventoryItemWrapper The item to set
     */
    default void add(InventoryItemWrapper inventoryItemWrapper) {
        set(nextFreeSlot(), inventoryItemWrapper);
    }

    /**
     * Sets an item into the next free slot. Free slots are slots where no item has been set and the
     * slot is not marked as reserved.
     *
     * @param itemStackWrapper The item stack to set
     * @param actionHandler    The action handler which should be called on click
     * @param actionArgs       the arguments for that click
     */
    default void add(
            ItemStackWrapper itemStackWrapper,
            String actionHandler,
            List<String> actionArgs) {
        set(nextFreeSlot(), InventoryItemWrapper.builder()
                .handle(itemStackWrapper)
                .actionHandler(actionHandler)
                .actionArguments(actionArgs)
                .build());
    }

    /**
     * Sets an item into the next free slot. Free slots are slots where no item has been set and the
     * slot is not marked as reserved.
     *
     * @param model The item stack model
     */
    default void add(LocalizedItemStackModel model) {
        set(nextFreeSlot(), InventoryItemWrapper.builder()
                .handle(Converters.convert(model, InventoryItemWrapper.class))
                .actionHandler(model.actionHandler())
                .actionArguments(model.actionArguments())
                .build());
    }

    /**
     * Sets an item into a given position in the container
     *
     * @param slot                 The slot the item will be located at
     * @param inventoryItemWrapper The item stack wrapper
     */
    default void set(int slot, InventoryItemWrapper inventoryItemWrapper) {
        itemMap().put(slot, inventoryItemWrapper);
    }

    /**
     * Sets an item into a given position in the container
     *
     * @param slot             The slot the item will be located at
     * @param itemStackWrapper The item stack wrapper
     * @param actionHandler    The handler that handles the click
     * @param actionArgs       Arguments for the action handler
     */
    default void set(
            int slot,
            ItemStackWrapper itemStackWrapper,
            String actionHandler,
            List<String> actionArgs) {
        set(slot, InventoryItemWrapper.builder()
                .handle(itemStackWrapper)
                .actionHandler(actionHandler)
                .actionArguments(actionArgs)
                .build());
    }

    /**
     * Sets an item into a given position in the container
     *
     * @param slot  The slot the item will be located at
     * @param model The item stack model
     */
    default void set(int slot, LocalizedItemStackModel model) {
        set(slot, InventoryItemWrapper.builder()
                .handle(Converters.convert(model, InventoryItemWrapper.class))
                .actionHandler(model.actionHandler())
                .actionArguments(model.actionArguments())
                .build());
    }

    /**
     * Returns the item stack from the given position
     *
     * @param slot slot
     * @return item
     */
    default InventoryItemWrapper get(int slot) {
        return itemMap().get(slot);
    }

}
