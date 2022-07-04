package dev.simplix.cirrus.common.item;

import dev.simplix.cirrus.common.effects.RGBColorChangeAnimation;
import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class Items {

    
    public CirrusItem rgb(String displayName, Color... colors) {
        return CirrusItem
                .animated(RGBColorChangeAnimation.fat(displayName, colors));
    }
}
