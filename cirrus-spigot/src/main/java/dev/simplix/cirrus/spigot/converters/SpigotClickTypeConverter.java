package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.common.converter.Converter;
import lombok.NonNull;
import org.bukkit.event.inventory.ClickType;

public class SpigotClickTypeConverter implements Converter<ClickType, dev.simplix.protocolize.api.ClickType> {

    @Override
    public dev.simplix.protocolize.api.ClickType convert(@NonNull ClickType src) {
        switch (src) {

            case LEFT:
                return dev.simplix.protocolize.api.ClickType.LEFT_CLICK;

            case RIGHT:
                return dev.simplix.protocolize.api.ClickType.RIGHT_CLICK;

            case DROP:
                return dev.simplix.protocolize.api.ClickType.DROP;

            case MIDDLE:
                return dev.simplix.protocolize.api.ClickType.CREATIVE_MIDDLE_CLICK;

            case NUMBER_KEY:
                return dev.simplix.protocolize.api.ClickType.NUMBER_BUTTON_1;

            case SHIFT_LEFT:
                return dev.simplix.protocolize.api.ClickType.SHIFT_LEFT_CLICK;

            case SHIFT_RIGHT:
                return dev.simplix.protocolize.api.ClickType.SHIFT_RIGHT_CLICK;

            case CONTROL_DROP:
                return dev.simplix.protocolize.api.ClickType.DROP_ALL;

            case DOUBLE_CLICK:
                return dev.simplix.protocolize.api.ClickType.DOUBLE_CLICK;

            case WINDOW_BORDER_LEFT:
                return dev.simplix.protocolize.api.ClickType.LEFT_CLICK_OUTSIDE_INVENTORY_HOLDING_NOTHING;

            case WINDOW_BORDER_RIGHT:
                return dev.simplix.protocolize.api.ClickType.RIGHT_CLICK_OUTSIDE_INVENTORY_HOLDING_NOTHING;

        }
        return null;
    }
}
