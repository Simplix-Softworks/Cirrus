package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.spigot.util.ReflectionClasses;
import lombok.NonNull;
import net.querz.nbt.io.NBTInputStream;
import net.querz.nbt.tag.CompoundTag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class NmsNbtQuerzNbtConverter implements Converter<Object, CompoundTag> {

    private static Method nbtCompressedStreamToolAMethod;

    static {
        try {
            nbtCompressedStreamToolAMethod = ReflectionClasses.nbtCompressedStreamTools()
                    .getMethod("a", ReflectionClasses.nbtTagCompound(), OutputStream.class);
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public CompoundTag convert(@NonNull Object src) {
        byte[] data = null;
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            nbtCompressedStreamToolAMethod.invoke(null, src, byteArrayOutputStream);
            data = byteArrayOutputStream.toByteArray();
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
        try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            return (CompoundTag) new NBTInputStream(byteArrayInputStream).readTag(99).getTag();
        } catch (final IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

}
