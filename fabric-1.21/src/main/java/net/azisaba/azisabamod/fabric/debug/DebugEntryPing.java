package net.azisaba.azisabamod.fabric.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class DebugEntryPing implements DebugScreenEntry {
    @Override
    public void display(@NonNull DebugScreenDisplayer debugScreenDisplayer, @Nullable Level level, @Nullable LevelChunk levelChunk, @Nullable LevelChunk levelChunk2) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        PlayerInfo playerInfo = minecraft.player.connection.getPlayerInfo(minecraft.player.getUUID());
        if (playerInfo != null) {
            int latency = playerInfo.getLatency();
            debugScreenDisplayer.addToGroup(Identifier.fromNamespaceAndPath("azisaba", "debug"), "[Azisaba] Ping: " + pingColor(latency) + latency + " §rms");
        }
    }

    @Override
    public boolean isAllowed(boolean bl) {
        return true;
    }

    @Override
    public @NonNull DebugEntryCategory category() {
        return AzisabaDebugScreenEntries.DEBUG_ENTRY_CATEGORY;
    }

    private static @NotNull String pingColor(int i) {
        if (i > 150) {
            return "§c";
        } else if (i >= 100) {
            return "§e";
        } else if (i >= 50) {
            return "§d";
        } else {
            return "§a";
        }
    }
}
