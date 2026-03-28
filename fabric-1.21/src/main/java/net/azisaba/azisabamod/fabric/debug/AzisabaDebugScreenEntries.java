package net.azisaba.azisabamod.fabric.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugEntryCategory;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class AzisabaDebugScreenEntries {
    public static final DebugEntryCategory DEBUG_ENTRY_CATEGORY = new DebugEntryCategory(Component.literal("AzisabaMod"), 0.9F);
    public static final Identifier TPS = register("tps", new DebugEntryTps());
    public static final Identifier PING = register("ping", new DebugEntryPing());
    public static final Identifier CURRENT_SCREEN = register("current_screen", new DebugEntryCurrentScreen());
    public static final Identifier BUILD_TOOL_MODE = register("build_tool_mode", new DebugEntryBuildToolMode());
    public static final Identifier ITEM_TOOLTIP_MYTHIC_TYPE = register("item_tooltip_mythic_type", new DebugEntryNoop());
    public static final Identifier ITEM_TOOLTIP_ITEM_MODEL = register("item_tooltip_item_model", new DebugEntryNoop());
    public static final Identifier ITEM_TOOLTIP_CUSTOM_MODEL_DATA = register("item_tooltip_custom_model_data", new DebugEntryNoop());
    public static final Identifier ITEM_TOOLTIP_SOULBOUND = register("item_tooltip_soulbound", new DebugEntryNoop());
    public static final Identifier ITEM_TOOLTIP_REPAIR_COST = register("item_tooltip_repair_cost", new DebugEntryNoop());
    public static final Identifier INDICATOR_IN_PLAYER_LIST = register("indicator_in_player_list", new DebugEntryNoop());

    private static @NonNull Identifier register(@NotNull String name, @NotNull DebugScreenEntry entry) {
        Identifier id = Identifier.fromNamespaceAndPath("azisaba", name);
        DebugScreenEntries.register(id, entry);
        return id;
    }

    public static boolean isEnabled(@NotNull Identifier id) {
        var status = Minecraft.getInstance().debugEntries.getStatus(id);
        return status == DebugScreenEntryStatus.ALWAYS_ON || status == DebugScreenEntryStatus.IN_OVERLAY;
    }
}
