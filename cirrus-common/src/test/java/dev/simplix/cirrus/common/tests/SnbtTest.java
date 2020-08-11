package dev.simplix.cirrus.common.tests;

import java.io.IOException;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class SnbtTest {

  @Test
  public void test() throws IOException {
    CompoundTag display = new CompoundTag();
    display.putInt("integer", 134);
    display.putBoolean("boolean", true);
    display.putFloat("float", 24934.3F);
    display.putDouble("double", 9999.21348D);
    display.putShort("short", (short) 12);
    display.putByte("byte", (byte) 3);

    CompoundTag compoundTag = new CompoundTag();
    compoundTag.put("display", display);

    String mojangson = SNBTUtil.toSNBT(compoundTag);
    Tag<?> after = SNBTUtil.fromSNBT(mojangson);

    Assertions.assertEquals(compoundTag, after);
    System.out.println("Querz SNBT - OK");
  }

}
