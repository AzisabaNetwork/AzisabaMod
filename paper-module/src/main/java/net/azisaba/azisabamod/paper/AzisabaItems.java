package net.azisaba.azisabamod.paper;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AzisabaItems {
    public static final Item KOTLIN = registerBlock(AzisabaBlocks.KOTLIN);

    public static Item registerBlock(Block block) {
        return registerBlock(block, BlockItem::new);
    }

    public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> factory) {
        return registerBlock(block, factory, new Item.Properties());
    }

    public static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> factory, Item.Properties properties) {
        return registerItem(blockIdToItemId(block.builtInRegistryHolder().key()), prop -> factory.apply(block, prop), properties.useBlockDescriptionPrefix());
    }

    private static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> blockId) {
        return ResourceKey.create(Registries.ITEM, blockId.identifier());
    }

    public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties) {
        Item item = factory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static void init() {}

    public static final Map<Item, Item> FALLBACK = Map.ofEntries(
            Map.entry(KOTLIN, Items.STONE)
    );
}
