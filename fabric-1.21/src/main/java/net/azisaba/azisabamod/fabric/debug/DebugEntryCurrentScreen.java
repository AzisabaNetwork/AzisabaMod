package net.azisaba.azisabamod.fabric.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class DebugEntryCurrentScreen implements DebugScreenEntry {
    @Override
    public void display(@NonNull DebugScreenDisplayer debugScreenDisplayer, @Nullable Level level, @Nullable LevelChunk levelChunk, @Nullable LevelChunk levelChunk2) {
        Minecraft minecraft = Minecraft.getInstance();
        String screenName = minecraft.screen == null ? "null" : minecraft.screen.getClass().getTypeName();
        debugScreenDisplayer.addToGroup(Identifier.fromNamespaceAndPath("azisaba", "debug"), "[Azisaba] Current Screen: " + screenName);
    }

    @Override
    public boolean isAllowed(boolean bl) {
        return true;
    }

    @Override
    public @NonNull DebugEntryCategory category() {
        return AzisabaDebugScreenEntries.DEBUG_ENTRY_CATEGORY;
    }
}
