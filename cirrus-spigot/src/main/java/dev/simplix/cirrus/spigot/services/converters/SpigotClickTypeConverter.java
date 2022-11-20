package dev.simplix.cirrus.spigot.services.converters;

import java.util.function.Function;
import lombok.NonNull;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

public class SpigotClickTypeConverter implements Function<ClickType, dev.simplix.protocolize.api.ClickType> {

  @Override
  public dev.simplix.protocolize.api.ClickType apply(@NonNull @NotNull ClickType src) {
    return switch (src) {
      case LEFT -> dev.simplix.protocolize.api.ClickType.LEFT_CLICK;
      case RIGHT -> dev.simplix.protocolize.api.ClickType.RIGHT_CLICK;
      case DROP -> dev.simplix.protocolize.api.ClickType.DROP;
      case MIDDLE -> dev.simplix.protocolize.api.ClickType.CREATIVE_MIDDLE_CLICK;
      case NUMBER_KEY -> dev.simplix.protocolize.api.ClickType.NUMBER_BUTTON_1;
      case SHIFT_LEFT -> dev.simplix.protocolize.api.ClickType.SHIFT_LEFT_CLICK;
      case SHIFT_RIGHT -> dev.simplix.protocolize.api.ClickType.SHIFT_RIGHT_CLICK;
      case CONTROL_DROP -> dev.simplix.protocolize.api.ClickType.DROP_ALL;
      case DOUBLE_CLICK -> dev.simplix.protocolize.api.ClickType.DOUBLE_CLICK;
      case WINDOW_BORDER_LEFT ->
          dev.simplix.protocolize.api.ClickType.LEFT_CLICK_OUTSIDE_INVENTORY_HOLDING_NOTHING;
      case WINDOW_BORDER_RIGHT ->
          dev.simplix.protocolize.api.ClickType.RIGHT_CLICK_OUTSIDE_INVENTORY_HOLDING_NOTHING;
      default -> dev.simplix.protocolize.api.ClickType.RIGHT_CLICK; // We
      // pretend its a right-click here
    };
  }
}
