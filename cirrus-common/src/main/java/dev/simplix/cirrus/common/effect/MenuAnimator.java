package dev.simplix.cirrus.common.effect;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class MenuAnimator {
    private final List<Animated> menus = new LinkedList<>();

    public boolean isEmpty() {
        return menus.isEmpty();
    }

    public void register(@NonNull Animated menu) {
        menus.add(menu);
    }

    public void remove(@NonNull Animated menu) {
        menus.remove(menu);
    }

    public void updateAll() {
        for (Animated menu : menus) {
            menu.update();
            menu.build();
        }
    }

}
