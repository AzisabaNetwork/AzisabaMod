package net.azisaba.azisabamod.fabric;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.azisaba.azisabamod.fabric.util.BuildToolUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class ModConfig {
    private static final Path configDir = new File("config").toPath();
    private static final Path configFile = configDir.resolve("azisabamod.json");
    private static final Gson GSON = new Gson();
    public String apiKey = "";
    public boolean blockPlacementPreview = true;
    public BuildToolUtil.BuildTool buildTool = null;

    public void load() {
        try {
            Path oldFile = configDir.resolve("azisabamod-config.json5");
            if (Files.exists(oldFile) && !Files.exists(configFile)) {
                Files.copy(oldFile, configFile);
            }
            if (Files.exists(configFile)) {
                JsonObject obj = GSON.fromJson(Files.readString(configFile), JsonObject.class);
                if (obj.has("apiKey")) {
                    apiKey = obj.get("apiKey").getAsString();
                }
                if (obj.has("blockPlacementPreview")) {
                    blockPlacementPreview = obj.get("blockPlacementPreview").getAsBoolean();
                }
                if (obj.has("buildToolType") && obj.has("buildToolSize")) {
                    buildTool = new BuildToolUtil.BuildTool(BuildToolUtil.Type.valueOf(obj.get("buildToolType").getAsString().toUpperCase()), obj.get("buildToolSize").getAsInt());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull CompletableFuture<Void> saveAsync() {
        return CompletableFuture.runAsync(this::save);
    }

    public void save() {
        JsonObject obj = new JsonObject();
        obj.addProperty("apiKey", apiKey);
        obj.addProperty("blockPlacementPreview", blockPlacementPreview);
        if (buildTool != null) {
            obj.addProperty("buildToolType", buildTool.type().name().toLowerCase());
            obj.addProperty("buildToolSize", buildTool.size());
        }
        try {
            Files.writeString(configFile, GSON.toJson(obj));
            Mod.LOGGER.info("Saved config");
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
                .addEntry(entryBuilder.startBooleanToggle(Component.translatable("text.config.azisabamod-config.option.blockPlacementPreview"), blockPlacementPreview)
                        .setTooltip(Component.translatable("text.config.azisabamod-config.option.blockPlacementPreview.tooltip"))
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> this.blockPlacementPreview = value)
                        .build())
                .addEntry(entryBuilder.startStrField(Component.translatable("text.config.azisabamod-config.option.apiKey"), apiKey)
                        .setTooltip(Component.translatable("text.config.azisabamod-config.option.apiKey.tooltip"))
                        .setDefaultValue("")
                        .setSaveConsumer(apiKey -> this.apiKey = apiKey)
                        .build());
        return builder.build();
    }
}
