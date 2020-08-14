package dev.simplix.cirrus.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Binder;
import com.google.inject.name.Names;
import dev.simplix.cirrus.api.i18n.*;
import dev.simplix.cirrus.common.mojangson.TagSerializer;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.common.aop.InjectorModule;
import net.querz.nbt.tag.CompoundTag;
import dev.simplix.cirrus.common.mojangson.TagDeserializer;

@InjectorModule("Cirrus")
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

  @Override
  public void configure(Binder binder) {
    super.configure(binder);
    binder.bind(Gson.class).annotatedWith(Names.named("cirrus")).toInstance(gson);
  }

}
