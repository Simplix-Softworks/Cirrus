package dev.simplix.cirrus.common.item;

import dev.simplix.cirrus.common.business.ItemStackWrapper;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import java.util.List;
import lombok.NonNull;
import net.md_5.bungee.api.chat.BaseComponent;
import net.querz.nbt.tag.CompoundTag;

public class ProtocolizeItemStackWrapper implements ItemStackWrapper {

    private final ItemStack itemStack;

    public ProtocolizeItemStackWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public String displayName() {
        return this.itemStack.displayName(true);
    }

    @Override
    public BaseComponent[] displayNameComponents() {
        return this.itemStack.displayName();
    }

    @Override
    public List<String> lore() {
        return this.itemStack.lore(true);
    }

    @Override
    public List<BaseComponent[]> loreComponents() {
        return this.itemStack.lore();
    }

    @Override
    public ItemType type() {
        return this.itemStack.itemType();
    }

    @Override
    public CompoundTag nbt() {
        return this.itemStack.nbtData();
    }

    @Override
    public int amount() {
        return this.itemStack.amount();
    }

    @Override
    public short durability() {
        return this.itemStack.durability();
    }

    @Override
    public void type(@NonNull ItemType type) {
        this.itemStack.itemType(type);
    }

    @Override
    public void displayName(@NonNull String displayName) {
        this.itemStack.displayName(displayName);
    }

    @Override
    public void displayNameComponents(BaseComponent... baseComponents) {
        this.itemStack.displayName(baseComponents);
    }

    @Override
    public void lore(@NonNull List<String> lore) {
        this.itemStack.lore(lore, true);
    }

    @Override
    public void loreComponents(@NonNull List<BaseComponent[]> lore) {
        this.itemStack.lore(lore, false);
    }

    @Override
    public void nbt(@NonNull CompoundTag compoundTag) {
        this.itemStack.nbtData(compoundTag);
    }

    @Override
    public void amount(int amount) {
        this.itemStack.amount((byte) amount);
    }

    @Override
    public void durability(short durability) {
        this.itemStack.durability(durability);
    }

    @Override
    public <T> T handle() {
        return (T) this.itemStack;
    }

    @Override
    public String toString() {
        return handle().toString();
    }
}
