package dev.simplix.cirrus.schematic.impl;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.actionhandler.RegisteredActionHandler;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.menu.MenuElement;
import dev.simplix.cirrus.menu.MenuRow;
import dev.simplix.cirrus.model.*;
import dev.simplix.cirrus.schematic.MenuSchematic;
import dev.simplix.cirrus.service.CapacityService;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.data.Sound;
import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.*;
import javax.annotation.Nullable;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class SimpleMenuSchematic implements MenuSchematic {

  protected final List<RegisteredActionHandler> actionHandlers = new ArrayList<>();
  private final Map<Integer, String> actionHandlerStringMap = new HashMap<>(0);
  @Builder.Default
  private String title = "Menu";
  @Builder.Default
  private InventoryType type = InventoryType.GENERIC_9X3;
  @Builder.Default
  private PlaceholderItem placeholderItem = null;
  @Builder.Default
  private Set<Integer> reservedSlots = new HashSet<>(0);
  @Builder.Default
  private BusinessItemMap businessItems = new BusinessItemMap();
  @Builder.Default
  private MenuContent rootItems = new MenuContent();
  @Builder.Default
  private Sound soundOnOpen = null;

  @Override
  public MenuSchematic copy() {
    return SimpleMenuSchematic
            .builder()
            .title(this.title)
            .placeholderItem(this.placeholderItem.copy())
            .reservedSlots(new HashSet<>(this.reservedSlots))
            .businessItems(this.businessItems.copy())
            .type(this.type)
            .rootItems(this.rootItems.copy())
            .build();
  }

  @Override
  public Locale locale() {
    return Objects.requireNonNull(Cirrus.defaultLocale(), "defaultLocale must not be null");
  }

  @Override
  @Nullable
  public String title() {
    return this.title;
  }

  @Override
  public InventoryType type() {
    return Objects.requireNonNull(this.type, "Type must not be null");
  }

  @Override
  public int typicalSize(int protocolVersion) {
    return type().getTypicalSize(protocolVersion);
  }

  @Override
  public Optional<PlaceholderItem> placeholderItem() {
    return Optional.ofNullable(this.placeholderItem);
  }

  @Override
  public int centerSlot() {
    final int pos = this.typicalSize() / 2;
    return this.typicalSize() % 2==1 ? pos:pos - 5;
  }

  @Override
  public MenuRow row(int row) {
    LinkedList<MenuElement> items = new LinkedList<>();

    int i = 9 * (row - 1);
    while (i < 9 * row) {
      items.add(element(i));
      i++;
    }

    return new MenuRow(items);
  }

  @Override
  public SimpleMenuSchematic set(CirrusItem item) {
    return (SimpleMenuSchematic) MenuSchematic.super.set(item);
  }

  @Override
  public MenuSchematic set(BaseItemStack item, int slot, @Nullable String actionHandler) {
    rootItems().put(slot, item);
    if (actionHandler!=null) {
      actionHandlerStringMap.put(slot, actionHandler);
    }

    return this;
  }

  @Override
  public MenuElement element(int slot) {
    return new MenuElement(this, get(slot), slot, actionHandlerString(slot).orElse(null));
  }

  @Nullable
  @Override
  public BaseItemStack get(int slot) {
    return this.rootItems.get(slot);
  }

  @Override
  public int add(BaseItemStack item, @Nullable String actionHandler) {

    final var capacity = Cirrus.service(CapacityService.class).capacity(type());
    for (int i = -1; i < capacity; i++) {
      if (!rootItems().containsKey(i) && !reservedSlots().contains(i)) {
        rootItems().put(i, item);
        return i;
      }
    }
    return -1;
  }

  @Override
  public Optional<ActionHandler> actionHandler(int slot) {
    return actionHandlerString(slot).flatMap(this::findActionHandler);
  }

  private Optional<ActionHandler> findActionHandler(String actionHandlerString) {
    for (RegisteredActionHandler registeredActionHandler : actionHandlers) {
      if (registeredActionHandler.name().equals(actionHandlerString)) {
        return Optional.of(registeredActionHandler.handler());
      }
    }
    return Optional.empty();
  }


  @Override
  public Optional<String> actionHandlerString(int slot) {
    return Optional.ofNullable(actionHandlerStringMap.get(slot));
  }

  @Override
  public SimpleMenuSchematic remove(int slot) {
    this.rootItems.remove(slot);
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj==null) {
      return false;
    }

    if (obj==this) {
      return true;
    }

    if (!(obj instanceof SimpleMenuSchematic)) {
      return false;
    }

    SimpleMenuSchematic other = (SimpleMenuSchematic) obj;
    return ((other.title==null && this.title()==null) || other.title().equals(title()))
            && other.type()==type()
            && other.placeholderItem().equals(placeholderItem())
            && other.reservedSlots().equals(reservedSlots())
            && other.businessItems().equals(businessItems())
            && other.rootItems().equals(rootItems());
  }
}

