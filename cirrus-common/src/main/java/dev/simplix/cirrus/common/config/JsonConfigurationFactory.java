package dev.simplix.cirrus.common.config;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.simplix.core.common.aop.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;
import dev.simplix.cirrus.api.business.ConfigurationFactory;
import dev.simplix.cirrus.api.model.MenuConfiguration;
import dev.simplix.cirrus.common.CirrusSimplixModule;
import sun.misc.IOUtils;

@Component(value = CirrusSimplixModule.class, parent = ConfigurationFactory.class)
public class JsonConfigurationFactory implements ConfigurationFactory {

  private final Gson gson;

  @Inject
  public JsonConfigurationFactory(@Named("cirrus") Gson gson) {
    this.gson = gson;
  }

  @Override
  public <T extends MenuConfiguration> T loadFile(String filename, Class<T> type) {
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
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T extends MenuConfiguration> T loadResource(String resourcePath, Class<T> type) {
    try (InputStream stream = CirrusSimplixModule.class.getResourceAsStream(resourcePath)) {
      String contents = new String(IOUtils.readFully(stream, -1, true), StandardCharsets.UTF_8);
      return gson.fromJson(contents, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void copyResourceToFile(String resource, File file) {
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      fileOutputStream.write(IOUtils.readFully(
          CirrusSimplixModule.class.getResourceAsStream(resource), -1, true));
      fileOutputStream.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
