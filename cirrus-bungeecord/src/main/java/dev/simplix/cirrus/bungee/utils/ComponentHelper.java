package dev.simplix.cirrus.bungee.utils;

import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.api.item.ItemStack;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;

@UtilityClass
public class ComponentHelper {

  private static final String STEVE_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTU5MTU3NDcyMzc4MywKICAicHJvZmlsZUlkIiA6ICI4NjY3YmE3MWI4NWE0MDA0YWY1NDQ1N2E5NzM0ZWVkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGV2ZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82ZDNiMDZjMzg1MDRmZmMwMjI5Yjk0OTIxNDdjNjlmY2Y1OWZkMmVkNzg4NWY3ODUwMjE1MmY3N2I0ZDUwZGUxIgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85NTNjYWM4Yjc3OWZlNDEzODNlNjc1ZWUyYjg2MDcxYTcxNjU4ZjIxODBmNTZmYmNlOGFhMzE1ZWE3MGUyZWQ2IgogICAgfQogIH0KfQ==";

  public void removeItalic(BaseComponent[] components) {
    for (BaseComponent component : components) {
      component.setItalic(false);
    }
  }

  public void fixItalic(BaseItemStack itemStack) {
    final List<BaseComponent[]> loreRaw = itemStack.lore(false);
    final List<BaseComponent[]> fixedLore = loreRaw
        .stream()
        .peek(ComponentHelper::removeItalic)
        .toList();

    itemStack.lore(fixedLore, false);

    BaseComponent[] displayName = itemStack.displayName(false);
    removeItalic(displayName);
    itemStack.displayName(displayName);
  }
}