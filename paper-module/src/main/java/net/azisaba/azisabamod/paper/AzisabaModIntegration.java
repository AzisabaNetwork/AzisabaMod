package net.azisaba.azisabamod.paper;

import net.azisaba.azisabamod.paper.mixin.world.item.ItemStackAccessor;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AzisabaModIntegration {
    public static final Logger LOGGER = LoggerFactory.getLogger(AzisabaModIntegration.class);
    public static final Set<UUID> INTEGRATION_ENABLED = Collections.synchronizedSet(new HashSet<>());

    public static boolean isModded(@NotNull UUID id) {
        return INTEGRATION_ENABLED.contains(id);
    }

    public static boolean isCustomItem(@Nullable ItemStack itemStack) {
        if (itemStack == null) return false;
        return itemStack.getItemHolder().getRegisteredName().startsWith("azisaba:");
    }

    public static boolean isCustomItem(@NotNull Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getNamespace().equals("azisaba");
    }

    /**
     * Transforms the given item stack to a fallback item if it is a custom item.
     * This method modifies the given item stack in place.
     * @param itemStack the item stack to transform
     * @return the transformed item stack
     */
    public static ItemStack transform(@Nullable ItemStack itemStack) {
        if (!isCustomItem(itemStack)) return itemStack;
        Item originalItem = itemStack.getItem();
        Item fallback = AzisabaItems.FALLBACK.getOrDefault(originalItem, Items.STONE);
        ((ItemStackAccessor) (Object) itemStack).setItem(fallback);
        DataComponentPatch patch = itemStack.getComponentsPatch();
        ((ItemStackAccessor) (Object) itemStack).setComponents(new PatchedDataComponentMap(fallback.components()));
        itemStack.applyComponents(patch);
        if (!itemStack.has(DataComponents.CUSTOM_NAME)) {
            itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(originalItem.getDescriptionId()));
        }
        return itemStack;
    }

    public static void transform(@Nullable Component component) {
        if (component == null) return;
        if (component.getStyle().getHoverEvent() instanceof HoverEvent.ShowItem(ItemStack item)) {
            transform(item);
        }
        component.getSiblings().forEach(AzisabaModIntegration::transform);
        if (component.getContents() instanceof TranslatableContents translatableContents) {
            for (Object arg : translatableContents.getArgs()) {
                if (arg instanceof Component) {
                    transform((Component) arg);
                }
            }
        }
    }

    public static boolean isCustomBlock(@Nullable BlockState blockState) {
        return blockState != null && BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).getNamespace().equals("azisaba");
    }

    public static BlockState getFallbackBlockState(@Nullable BlockState blockState) {
        if (blockState == null) return null;
        Block originalBlock = blockState.getBlock();
        Block fallback = AzisabaBlocks.FALLBACK.getOrDefault(originalBlock, Blocks.STONE);
        return fallback.defaultBlockState();
    }
}
