package dev.simplix.cirrus.gson;

import com.google.gson.*;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import net.querz.nbt.tag.CompoundTag;

public class ItemStackDeserializer implements JsonDeserializer<BaseItemStack> {

  @Override
  public BaseItemStack deserialize(
      JsonElement json,
      Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {

    final JsonObject asJsonObject = json.getAsJsonObject();
    final ItemType type = ItemType.valueOf(asJsonObject.get("type").getAsString());
    final int amount = asJsonObject.get("amount").getAsInt();
    final byte durability = asJsonObject.get("durability").getAsByte();
    final int hideflags = asJsonObject.get("hide-flags").getAsInt();
    final JsonElement nbtRaw = asJsonObject.get("nbt");
    final CompoundTag nbt = nbtRaw == null
        ? new CompoundTag()
        : context.deserialize(nbtRaw, CompoundTag.class);
    final String displayName = asJsonObject.get("display-name") != null ? asJsonObject
        .get("display-name")
        .getAsString() : "";
    final List<String> lore = asJsonObject.get("lore") != null
        ? context.deserialize(asJsonObject.get("lore"), List.class)
        : null;

    final ItemStack itemStack = new ItemStack(type, amount, durability)
        .displayName(displayName)
        .nbtData(nbt);

    itemStack.lore(lore, true);
    itemStack.hideFlags(hideflags);

    return itemStack;
  }
}
