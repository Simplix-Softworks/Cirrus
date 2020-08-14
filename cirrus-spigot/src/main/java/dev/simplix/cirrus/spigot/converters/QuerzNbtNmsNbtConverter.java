package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.core.common.converter.Converter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.zip.GZIPOutputStream;
import net.querz.nbt.io.NBTOutputStream;
import net.querz.nbt.tag.CompoundTag;

public class QuerzNbtNmsNbtConverter implements Converter<CompoundTag, Object> {

    private static Method nbtCompressedStreamToolAMethod;

    static {
        try {
            nbtCompressedStreamToolAMethod = ReflectionUtil
                .getClass("{nms}.NBTCompressedStreamTools").getMethod("a", InputStream.class);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object convert(final CompoundTag src) {
        byte[] data = null;
        try(final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            new NBTOutputStream(gzipOutputStream).writeTag(src, 99);
            gzipOutputStream.close();
            data = byteArrayOutputStream.toByteArray();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        try(final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            return nbtCompressedStreamToolAMethod.invoke(null, byteArrayInputStream);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
