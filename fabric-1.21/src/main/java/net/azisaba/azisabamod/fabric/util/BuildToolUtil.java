package net.azisaba.azisabamod.fabric.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

public class BuildToolUtil {
    public static @Nullable BuildTool identifyOptions(ChestMenu menu) {
        ItemStack line = menu.getSlot(0).getItem();
        if (line.isEnchanted()) {
            return new BuildTool(Type.LINE, line.getCount());
        }
        ItemStack square = menu.getSlot(2).getItem();
        if (square.isEnchanted()) {
            return new BuildTool(Type.SQUARE, square.getCount());
        }
        return null;
    }

    public static boolean isBuildTool(ItemStack stack) {
        if (stack.getItem() != Items.BLAZE_ROD) return false;
        var loreData = stack.get(DataComponents.LORE);
        if (loreData == null) return false;
        if (loreData.lines().size() != 3) return false;
        if (!stack.isEnchanted()) return false;
        var customModelData = stack.get(DataComponents.CUSTOM_MODEL_DATA);
        if (customModelData == null) return false;
        Float f = customModelData.getFloat(0);
        if (f == null || f != 15.0f) return false;
        var customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) return false;
        if (!stack.has(DataComponents.UNBREAKABLE)) return false;
        return customData.copyTag().getCompoundOrEmpty("PublicBukkitValues").contains("minecraft:admin_item");
    }

    public enum Type {
        LINE,
        SQUARE,
    }

    public record BuildTool(Type type, int size) {}
}
