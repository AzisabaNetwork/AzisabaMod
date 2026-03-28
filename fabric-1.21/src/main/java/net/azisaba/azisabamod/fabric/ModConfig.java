package net.azisaba.azisabamod.fabric;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ModConfig {
    private static final Path configDir = new File("config").toPath();
    private static final Path configFile = configDir.resolve("azisabamod.json");
    private static final Gson GSON = new Gson();
    public String apiKey = "";

    public void load() {
        try {
            Path oldFile = configDir.resolve("azisabamod-config.json5");
            if (Files.exists(oldFile) && !Files.exists(configFile)) {
                Files.copy(oldFile, configFile);
            }
            if (Files.exists(configFile)) {
                JsonObject obj = GSON.fromJson(Files.readString(configFile), JsonObject.class);
                apiKey = obj.get("apiKey").getAsString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        JsonObject obj = new JsonObject();
        obj.addProperty("apiKey", apiKey);
        try {
            Files.writeString(configFile, GSON.toJson(obj));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull Screen createConfigScreen(@Nullable Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.nullToEmpty("Azisaba Mod"))
                .setSavingRunnable(this::save);
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        builder.getOrCreateCategory(Component.nullToEmpty("General"))
                .addEntry(entryBuilder.startStrField(Component.translatable("text.config.azisabamod-config.option.apiKey"), apiKey)
                        .setTooltip(Component.translatable("text.config.azisabamod-config.option.apiKey.tooltip"))
                        .setDefaultValue("")
                        .setSaveConsumer(apiKey -> this.apiKey = apiKey)
                        .build());
        return builder.build();
    }
}
