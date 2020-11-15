package dev.simplix.cirrus.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Binder;
import com.google.inject.name.Names;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.i18n.*;
import dev.simplix.cirrus.common.mojangson.TagSerializer;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.common.aop.ApplicationModule;
import dev.simplix.core.common.converter.Converter;
import dev.simplix.core.common.converter.Converters;
import net.querz.nbt.tag.CompoundTag;
import dev.simplix.cirrus.common.mojangson.TagDeserializer;

@ApplicationModule("Cirrus")
public class CirrusSimplixModule extends AbstractSimplixModule {

  private final Gson gson = new GsonBuilder()
      .registerTypeAdapter(CompoundTag.class, new TagSerializer())
      .registerTypeAdapter(CompoundTag.class, new TagDeserializer())
      .registerTypeAdapter(LocalizedString.class, new LocalizedStringSerializer())
      .registerTypeAdapter(LocalizedString.class, new LocalizedStringDeserializer())
      .registerTypeAdapter(LocalizedStringList.class, new LocalizedStringListSerializer())
      .registerTypeAdapter(LocalizedStringList.class, new LocalizedStringListDeserializer())
      .setPrettyPrinting()
      .create();

  static {
    Converters.register(
        InventoryItemWrapper.class, ItemStackWrapper.class,
        (Converter<InventoryItemWrapper, ItemStackWrapper>) InventoryItemWrapper::wrapper);
  }

  @Override
  public void configure(Binder binder) {
    super.configure(binder);
    binder.bind(Gson.class).annotatedWith(Names.named("cirrus")).toInstance(gson);
  }

}
