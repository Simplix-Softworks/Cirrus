package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.spigot.util.ReflectionClasses;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.inventory.ItemStack;

@Slf4j
public class BukkitItemStackConverter implements Converter<ItemStack, dev.simplix.protocolize.api.item.ItemStack> {

    private static Class<?> craftItemStackClass;
    private static Class<?> itemStackNMSClass;

    static {
        try {
            craftItemStackClass = ReflectionUtil.getClass("{obc}.inventory.CraftItemStack");
            itemStackNMSClass = ReflectionClasses.itemStackClass();
        } catch (Exception exception) {
            log.error("Could not get required classes", exception);
        }
    }

    @Override
    public dev.simplix.protocolize.api.item.ItemStack convert(@NonNull ItemStack src) {
        try {
            dev.simplix.protocolize.api.item.ItemStack out = new dev.simplix.protocolize.api.item.ItemStack(
                    Converters.convert(src.getData(), ItemType.class),
                    src.getAmount(),
                    src.getDurability());
            Object handle = ReflectionUtil.fieldValue(craftItemStackClass, src, "handle");
            out.nbtData(Converters.convert(
                    itemStackNMSClass.getMethod("getTag").invoke(handle),
                    CompoundTag.class));
            return out;
        } catch (Exception exception) {
            throw new IllegalArgumentException("Could not convert item stack", exception);
        } catch (Throwable throwable) {
            throw new IllegalArgumentException("Fatal error converting item stack", throwable);
        }
    }

}
