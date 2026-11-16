package net.azisaba.azisabamod.paper;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class AzisabaBlocks {
    private static final Set<Block> BLOCKS = new HashSet<>();
    public static final Block KOTLIN = register(
            "kotlin",
            BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F)
    );

    private static Block register(ResourceKey<Block> resourceKey, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        Block block = factory.apply(properties.setId(resourceKey));
        BLOCKS.add(block);
        return Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
    }

    private static Block register(ResourceKey<Block> resourceKey, BlockBehaviour.Properties properties) {
        return register(resourceKey, Block::new, properties);
    }

    private static ResourceKey<Block> vanillaBlockId(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("azisaba", name));
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        return register(vanillaBlockId(name), factory, properties);
    }

    private static Block register(String name, BlockBehaviour.Properties properties) {
        return register(name, Block::new, properties);
    }

    public static void init() {}

    static {
        for (Block block : BLOCKS) {
            for (BlockState blockState : block.getStateDefinition().getPossibleStates()) {
                Block.BLOCK_STATE_REGISTRY.add(blockState);
                blockState.initCache();
            }
        }
    }

    public static final Map<Block, Block> FALLBACK = Map.ofEntries(
            Map.entry(KOTLIN, Blocks.STONE)
    );
}
