package dev.simplix.cirrus.common;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;

@UtilityClass
public class Utils {
    public void removeItalic(BaseComponent[] components) {
        for (BaseComponent component : components) {
            component.setItalic(false);
        }
    }
}
