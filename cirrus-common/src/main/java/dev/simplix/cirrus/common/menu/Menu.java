package dev.simplix.cirrus.common.menu;

import dev.simplix.cirrus.common.business.ItemStackWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.Locale;
import java.util.function.Supplier;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public interface Menu extends ErrorProne {

    /**
     * @return The inventory type
     */
    InventoryType inventoryType();

    void registerActionHandler(@NonNull String name, @NonNull AutoCancellingActionHandler actionHandler);

    /**
     * Registers a new action handler
     *
     * @param name          the name
     * @param actionHandler the action handler
     */
    void registerActionHandler(@NonNull String name, @NonNull ActionHandler actionHandler);

    /**
     * ActionHandler which handles clicks in empty space
     *
     * @param actionHandler the action handler
     */
    void customActionHandler(@NonNull ActionHandler actionHandler);

    /**
     * This opens the menu for the specified player
     */
    void open();

    /**
     * This builds the menu
     */
    void build();

    /**
     * ActionHandler which handles clicks in empty space
     *
     * @return The custom action handler
     */
    ActionHandler customActionHandler();

    /**
     * Returns an action handler by its name
     *
     * @param name the name of the desired action handler
     * @return the action handler or null if not found
     */
    @Nullable
    ActionHandler actionHandler(@NonNull String name);

    /**
     * Returns the {@link Container} for the upper inventory.
     *
     * @return container
     */
    Container topContainer();

    /**
     * Returns the {@link Container} for the lower inventory.
     *
     * @return container
     */
    Container bottomContainer();

    /**
     * Returns the title of the menu
     *
     * @return title string
     */
    String title();

    /**
     * Sets the title of the menu
     *
     * @param title title
     */
    void title(@NonNull String title);

    /**
     * @return The locale the menu is in
     */
    Locale locale();

    /**
     * Returns the owner of this menu. The current viewer.
     *
     * @return The player
     */
    PlayerWrapper player();

    /**
     * Returns a {@link Supplier} which returns a string array used for placeholder replacement.
     *
     * @return Supplier for string array
     */
    Supplier<String[]> replacements();

    /**
     * Sets a {@link Supplier} which returns a string array used for placeholder replacement.
     *
     * @param supplier supplier
     */
    void replacements(Supplier<String[]> supplier);

    /**
     * Called when leaving the current menu.
     *
     * @param inventorySwitch true if a new menu was built within the last 55ms
     */
    default void handleClose(boolean inventorySwitch) {
    }

    /**
     * Converts an object into an ItemStackWrapper.
     *
     * @param object the object to convert
     * @return the wrapper object
     */
    default ItemStackWrapper wrapItemStack(@NonNull Object object) {
        return Converters.convert(object, ItemStackWrapper.class);
    }

}
