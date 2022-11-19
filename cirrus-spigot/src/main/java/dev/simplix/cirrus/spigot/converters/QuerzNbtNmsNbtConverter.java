package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.spigot.util.ReflectionClasses;
import lombok.NonNull;
import net.querz.nbt.io.NBTOutputStream;
import net.querz.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.zip.GZIPOutputStream;

public class QuerzNbtNmsNbtConverter implements Function<CompoundTag, Object> {

  private static Method nbtCompressedStreamToolAMethod;

  static {
    try {
      nbtCompressedStreamToolAMethod = ReflectionClasses
          .nbtCompressedStreamTools()
          .getMethod("a", InputStream.class);
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Object apply(@NonNull @NotNull CompoundTag src) {
    byte[] data = null;
    try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
      new NBTOutputStream(gzipOutputStream).writeTag(src, 99);
      gzipOutputStream.close();
      data = byteArrayOutputStream.toByteArray();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
      return nbtCompressedStreamToolAMethod.invoke(null, byteArrayInputStream);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }

}
