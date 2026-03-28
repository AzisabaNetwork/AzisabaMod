package net.azisaba.azisabamod.fabric.debug;

import net.azisaba.azisabamod.fabric.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class DebugEntryBuildToolMode implements DebugScreenEntry {
    @Override
    public void display(@NonNull DebugScreenDisplayer debugScreenDisplayer, @Nullable Level level, @Nullable LevelChunk levelChunk, @Nullable LevelChunk levelChunk2) {
        debugScreenDisplayer.addToGroup(Identifier.fromNamespaceAndPath("azisaba", "debug"), "[Azisaba] Build Tool Mode: " + Mod.CONFIG.buildTool);
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
