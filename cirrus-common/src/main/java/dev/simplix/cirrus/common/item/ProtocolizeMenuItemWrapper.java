package dev.simplix.cirrus.common.item;

import dev.simplix.cirrus.common.business.MenuItemWrapper;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import net.md_5.bungee.api.chat.BaseComponent;
import net.querz.nbt.tag.CompoundTag;

import java.util.List;
import java.util.stream.Collectors;

public class ProtocolizeMenuItemWrapper implements MenuItemWrapper {

    private final ItemStack itemStack;

    public ProtocolizeMenuItemWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public String displayName() {
        return this.itemStack.displayName().asLegacyText();
    }

    @Override
    public BaseComponent[] displayNameComponents() {
        return (BaseComponent[]) this.itemStack.displayName().asComponent();
    }

    @Override
    public List<String> lore() {
        return this.itemStack.lore().stream().map(ChatElement::asLegacyText).collect(Collectors.toList());
    }

    @Override
    public List<BaseComponent[]> loreComponents() {
        return (List<BaseComponent[]>) this.itemStack.lore().stream().map(ChatElement::asComponent).collect(Collectors.toList());
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
        this.itemStack.displayName(ChatElement.ofLegacyText(displayName));
    }

    @Override
    public void displayNameComponents(BaseComponent... baseComponents) {
        this.itemStack.displayName(ChatElement.of(baseComponents));
    }

    @Override
    public void lore(@NonNull List<String> lore) {
        this.itemStack.lore(lore.stream().map(ChatElement::ofLegacyText).collect(Collectors.toList()));
    }

    @Override
    public void loreComponents(@NonNull List<BaseComponent[]> lore) {
        this.itemStack.lore(lore.stream().map(ChatElement::of).collect(Collectors.toList()));
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
