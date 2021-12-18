package dev.simplix.cirrus.common.business;

import dev.simplix.protocolize.data.ItemType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.querz.nbt.tag.CompoundTag;

@Accessors(fluent = true)
@Builder
public class InventoryItemWrapper implements ItemStackWrapper {

    private final ItemStackWrapper handle;

    @Getter
    @Setter
    private String actionHandler;
    @Getter
    @Setter
    private List<String> actionArguments;

    @Override
    public String displayName() {
        return this.handle.displayName();
    }

    @Override
    public BaseComponent[] displayNameComponents() {
        return this.handle.displayNameComponents();
    }

    @Override
    public List<String> lore() {
        return this.handle.lore();
    }

    @Override
    public List<BaseComponent[]> loreComponents() {
        return this.handle.loreComponents();
    }

    @Override
    public ItemType type() {
        return this.handle.type();
    }

    @Override
    public CompoundTag nbt() {
        return this.handle.nbt();
    }

    @Override
    public int amount() {
        return this.handle.amount();
    }

    @Override
    public short durability() {
        return this.handle.durability();
    }

    @Override
    public void type(@NonNull ItemType type) {
        this.handle.type(type);
    }

    @Override
    public void displayName(@NonNull String displayName) {
        this.handle.displayName(displayName);
    }

    @Override
    public void displayNameComponents(@NonNull BaseComponent... baseComponents) {
        this.handle.displayNameComponents(baseComponents);
    }

    @Override
    public void lore(@NonNull List<String> lore) {
        this.handle.lore(lore);
    }

    @Override
    public void loreComponents(@NonNull List<BaseComponent[]> lore) {
        this.handle.loreComponents(lore);
    }

    @Override
    public void nbt(@NonNull CompoundTag tag) {
        this.handle.nbt(tag);
    }

    @Override
    public void amount(int amount) {
        this.handle.amount(amount);
    }

    @Override
    public void durability(short durability) {
        this.handle.durability(durability);
    }

    @Override
    public <T> T handle() {
        return this.handle.handle();
    }

    public ItemStackWrapper wrapper() {
        return this.handle;
    }

    @Override
    public String toString() {
        return "InventoryItemWrapper{" +
                "handle=" + this.handle +
                ", actionHandler='" + this.actionHandler + '\'' +
                ", actionArguments=" + this.actionArguments +
                '}';
    }
}
