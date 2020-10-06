package dev.simplix.cirrus.common.config;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.simplix.cirrus.api.business.ConfigurationFactory;
import dev.simplix.cirrus.api.model.MenuConfiguration;
import dev.simplix.cirrus.common.CirrusSimplixModule;
import dev.simplix.core.common.aop.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;
import lombok.NonNull;

@Component(value = CirrusSimplixModule.class, parent = ConfigurationFactory.class)
public class JsonConfigurationFactory implements ConfigurationFactory {

  private final Gson gson;

  @Inject
  public JsonConfigurationFactory(@Named("cirrus") Gson gson) {
    this.gson = gson;
  }

  @Override
  public <T extends MenuConfiguration> T loadFile(@NonNull String filename,@NonNull  Class<T> type) {
    File file = new File(filename);
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      copyResourceToFile("/cirrus/"+type.getSimpleName()+".json", file);
    }
    try (
        InputStreamReader reader = new InputStreamReader(
            new FileInputStream(file),
            StandardCharsets.UTF_8)) {
      return gson.fromJson(reader, type);
    } catch (IOException ioException) {
      throw new RuntimeException(ioException);
    }
  }

  @Override
  public <T extends MenuConfiguration> T loadResource(@NonNull String resourcePath,@NonNull  Class<T> type) {
    try (InputStream stream = CirrusSimplixModule.class.getResourceAsStream(resourcePath)) {
      String contents = new String(ByteStreams.toByteArray(stream), StandardCharsets.UTF_8);
      return gson.fromJson(contents, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void copyResourceToFile(@NonNull String resource, @NonNull File file) {
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      fileOutputStream.write(ByteStreams.toByteArray(
          CirrusSimplixModule.class.getResourceAsStream(resource)));
      fileOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
