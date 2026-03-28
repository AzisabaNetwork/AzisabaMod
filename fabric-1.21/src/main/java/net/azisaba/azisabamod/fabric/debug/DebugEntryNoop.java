package net.azisaba.azisabamod.fabric.debug;

import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class DebugEntryNoop implements DebugScreenEntry {
    @Override
    public void display(@NonNull DebugScreenDisplayer debugScreenDisplayer, @Nullable Level level, @Nullable LevelChunk levelChunk, @Nullable LevelChunk levelChunk2) {
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
