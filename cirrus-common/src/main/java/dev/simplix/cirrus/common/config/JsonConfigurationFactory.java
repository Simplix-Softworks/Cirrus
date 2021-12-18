package dev.simplix.cirrus.common.config;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.business.ConfigurationFactory;
import dev.simplix.cirrus.common.configuration.MenuConfiguration;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import lombok.NonNull;

public class JsonConfigurationFactory implements ConfigurationFactory {

    private final Gson gson;

    public JsonConfigurationFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T extends MenuConfiguration> T loadFile(
            @NonNull String filename,
            @NonNull Class<T> type) {
        File file = new File(filename);
        if (!file.exists()) {
            if (file.getParentFile()!=null) {
                file.getParentFile().mkdirs();
            }
            copyResourceToFile("/cirrus/" + type.getSimpleName() + ".json", file);
        }
        try (
                InputStreamReader reader = new InputStreamReader(
                        new FileInputStream(file),
                        StandardCharsets.UTF_8)) {
            return this.gson.fromJson(reader, type);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override
    public <T extends MenuConfiguration> T loadResource(
            @NonNull String resourcePath,
            @NonNull Class<?> caller,
            @NonNull Class<T> type) {
        try (InputStream stream = caller.getResourceAsStream(resourcePath)) {
            if (stream==null) {
                throw new NoSuchFileException("Unable to find resource " + resourcePath);
            }
            String contents = new String(ByteStreams.toByteArray(stream), StandardCharsets.UTF_8);
            return this.gson.fromJson(contents, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyResourceToFile(@NonNull String resource, @NonNull File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(ByteStreams.toByteArray(
                    Cirrus.class.getResourceAsStream(resource)));
            fileOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
